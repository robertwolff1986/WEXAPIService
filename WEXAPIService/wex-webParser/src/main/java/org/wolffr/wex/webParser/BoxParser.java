package org.wolffr.wex.webParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wolffr.wex.analyzer.MailService;
import org.wolffr.wex.mongo.ConfigurationStore;
import org.wolffr.wex.webParser.model.ChatMsg;

@Singleton
@Startup
public class BoxParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoxParser.class);

	@EJB
	private ConfigurationStore configurationStore;
	@EJB
	MailService mailService;

	private Map<Integer, ChatMsg> msgMap = new TreeMap<>();
	private List<String> vipList;

	public void run() throws InterruptedException {

		try {
			if (vipList == null)
				initializeVipList();
			check();
			Thread.sleep(500);
			run();
		} catch (Exception e) {
			Thread.sleep(500);
			run();
		}
	}

	private void initializeVipList() {
		vipList = Arrays.asList(configurationStore.getConfiguration().getBoxVip().split(","));
	}

	private void check() throws IOException {
		Document doc = Jsoup.connect("https://wex.nz").get();
		List<ChatMsg> msgList = Arrays.asList(doc.getElementById("nChat").toString().split("\n")).stream()
				.filter(line -> line.contains("<p id=")).map(this::convertMsgStringToMsg)
				.filter(msg -> !msgMap.containsKey(Integer.valueOf(msg.getMsgId()))).collect(Collectors.toList());
		msgList.stream().forEach(msg -> msgMap.put(Integer.valueOf(msg.getMsgId()), msg));
		checkVIPS(msgList);
	}

	private void checkVIPS(List<ChatMsg> msgList) {
		List<ChatMsg> importantMsgs = msgList.stream().filter(msg -> vipList.contains(msg.getUsername()))
				.collect(Collectors.toList());
		if (!importantMsgs.isEmpty()) {
			importantMsgs.stream().forEach(this::sendMail);
		}
	}

	private void sendMail(ChatMsg msg) {
		mailService.sendTrollBoxMsg(msg.toString(), msg.getUsername());
	}

	private ChatMsg convertMsgStringToMsg(String msgString) {
		return new ChatMsg(getUsernameString(msgString), getIdString(msgString), getUserMessageString(msgString),
				getTimeString(msgString));
	}

	private String getUserMessageString(String msgString) {
		return msgString.substring(msgString.indexOf("<span>") + 6, msgString.indexOf("</span>"));
	}

	private String getUsernameString(String msgString) {
		String coarseUserNameString = msgString.substring(msgString.indexOf(", &quot;") + 8,
				msgString.indexOf(", &quot;") + 38);
		return coarseUserNameString.substring(0, coarseUserNameString.indexOf("&quot;"));
	}

	private String getTimeString(String msgString) {
		return msgString.substring(msgString.indexOf("a title=\"") + 9, msgString.indexOf("a title=\"") + 26);
	}

	private String getIdString(String msgString) {
		String coarseMsgIdString = msgString.substring(msgString.indexOf("id=\"msg") + 7,
				msgString.indexOf("id=\"msg") + 20);
		return coarseMsgIdString.substring(0, coarseMsgIdString.indexOf("\""));
	}
}
