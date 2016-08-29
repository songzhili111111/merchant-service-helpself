package com.cn.function.login;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.common.JsonReader;
import com.cn.common.MessageAndCode;
import com.cn.common.Utils;
import com.cn.dao.inter.login.BriberyMoneyDao;
import com.cn.entity.login.BriberyMoney;

/**
 * 红包配置控制器
 * @author songzhili
 * 2016年7月14日下午1:38:15
 */
@Controller
@RequestMapping("/briberyMoney")
public class BriberyMoneyConfigureController {
  
	@Autowired
	private BriberyMoneyDao briberyMoneyDao;
	
	@ResponseBody
	@RequestMapping(value="/create",produces="application/json;charset=UTF-8")
	public String createBriberyMoney(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			List<BriberyMoney> moneys = Utils.converseMapToObject(BriberyMoney.class, source);
			if(moneys != null && !moneys.isEmpty()){
				for(BriberyMoney money : moneys){
					money.setCreateTime(new Date());
					briberyMoneyDao.add(money);
				}
			}
		} catch (Exception ex) {
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return node.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
	public String deleteBriberyMoney(@RequestBody Map<String, Object> source){
		
		String briberyMoneyId = Utils.obtainValue("briberyMoneyId", source);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		if(Utils.isEmpty(briberyMoneyId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "briberyMoneyId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			briberyMoneyDao.delete(briberyMoneyId);
		} catch (Exception ex) {
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return node.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/update",produces="application/json;charset=UTF-8")
	public String updateBriberyMoney(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		List<BriberyMoney> moneys = Utils.converseMapToObject(BriberyMoney.class, source);
		if(moneys != null && !moneys.isEmpty()){
			try {
				for(BriberyMoney money : moneys){
					String briberyMoneyId = money.getBriberyMoneyId();
					if(Utils.isEmpty(briberyMoneyId)){
						node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
						node.put(MessageAndCode.RESPONSEMESSAGE, "briberyMoneyId是必填参数,不能为空,请检查你的参数!!!!!");
						return node.toString();
					}
					BriberyMoney moneyOne = briberyMoneyDao.obtain(briberyMoneyId);
					boolean update = false;
					if(!Utils.isEmpty(money.getBriberyMoneyExplain())){
						moneyOne.setBriberyMoneyExplain(money.getBriberyMoneyExplain());
						update = true;
					}
					if(!Utils.isEmpty(money.getBriberyMoneyName())){
						moneyOne.setBriberyMoneyName(money.getBriberyMoneyName());
						update = true;
					}
					if(!Utils.isEmpty(money.getBriberyMoneyRule())){
						moneyOne.setBriberyMoneyRule(money.getBriberyMoneyRule());
						update = true;
					}
					if(!Utils.isEmpty(money.getBriberyMoneyType())){
						moneyOne.setBriberyMoneyType(money.getBriberyMoneyType());
						update = true;
					}
					if(!Utils.isEmpty(money.getExpandParam())){
						moneyOne.setExpandParam(money.getExpandParam());
						update = true;
					}
					if(!Utils.isEmpty(money.getStartTime())){
						moneyOne.setStartTime(money.getStartTime());
						update = true;
					}
					if(!Utils.isEmpty(money.getEndTime())){
						moneyOne.setEndTime(money.getEndTime());
						update = true;
					}
					if(Utils.isEmpty(money.getThirdPartyId())){
						moneyOne.setThirdPartyId(money.getThirdPartyId());
						update = true;
					}
					if(update){
						briberyMoneyDao.update(moneyOne);
					}
				}
			} catch (Exception ex) {
				node.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
				node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
				return node.toString();
			}
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/get",produces="application/json;charset=UTF-8")
	public String obtainBriberyMoney(@RequestBody Map<String, Object> source){
		
		String briberyMoneyId = Utils.obtainValue("briberyMoneyId", source);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		if(Utils.isEmpty(briberyMoneyId)){
			node.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			node.put(MessageAndCode.RESPONSEMESSAGE, "briberyMoneyId是必填参数,不能为空,请检查你的参数!!!!!");
			return node.toString();
		}
		try {
			BriberyMoney money = briberyMoneyDao.obtain(briberyMoneyId);
			node.put("briberyMoneyId", money.getBriberyMoneyId());
			node.put("briberyMoneyExplain", money.getBriberyMoneyExplain());
			node.put("briberyMoneyName", money.getBriberyMoneyName());
			node.put("briberyMoneyRule", fromStringToArrayNode(money.getBriberyMoneyRule()));
			node.put("briberyMoneyType", money.getBriberyMoneyType());
			node.put("endTime", money.getEndTime());
			node.put("startTime", money.getStartTime());
			node.put("expandParam", money.getExpandParam());
			node.put("thirdPartyId", money.getThirdPartyId());
		} catch (Exception ex) {
			ObjectNode nodeOne = mapper.createObjectNode();
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return nodeOne.toString();
		}
		node.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		node.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return node.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/list",produces="application/json;charset=UTF-8")
	public String obtainBriberyMoneyList(@RequestBody Map<String, Object> source){
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode nodes = mapper.createArrayNode();
		ObjectNode nodeOne = mapper.createObjectNode();
		List<String> requestParams = Utils.obtainParams("briberyMoneyName");
		Map<String, String> requestMap = Utils.obtianValues(source, requestParams);
		String briberyMoneyName = requestMap.get("briberyMoneyName");
		String currentPageOne = requestMap.get("curragePage");
		String pageSizeOne = requestMap.get("pageSize");
		if(Utils.isEmpty(currentPageOne) 
				|| Utils.isEmpty(pageSizeOne)){
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.MUSTPARAMMISSING);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, "curragePage或pageSize是必填参数,不能为空,请检查你的参数!!!!!");
			return nodeOne.toString();
		}
		Integer currentPage = Integer.parseInt(currentPageOne);
		Integer pageSize = Integer.parseInt(pageSizeOne);
		Integer totalNumber = 0;
		try {
			List<BriberyMoney> list = null;
			if(!Utils.isEmpty(briberyMoneyName)){
				list = briberyMoneyDao.obtainListByBriberyMoneyName(briberyMoneyName, BriberyMoney.class, currentPage-1, pageSize);
				totalNumber = briberyMoneyDao.countByBriberyMoneyName(briberyMoneyName);
			}else{
				list = briberyMoneyDao.obtainList(BriberyMoney.class,currentPage-1, pageSize);
				totalNumber = briberyMoneyDao.count();
			}
			if(list != null && !list.isEmpty()){
				for(BriberyMoney money : list){
					ObjectNode node = mapper.createObjectNode();
					node.put("briberyMoneyId", money.getBriberyMoneyId());
					node.put("briberyMoneyExplain", money.getBriberyMoneyExplain());
					node.put("briberyMoneyName", money.getBriberyMoneyName());
					node.put("briberyMoneyRule", fromStringToArrayNode(money.getBriberyMoneyRule()));
					node.put("briberyMoneyType", money.getBriberyMoneyType());
					node.put("endTime", money.getEndTime());
					node.put("startTime", money.getStartTime());
					node.put("expandParam", money.getExpandParam());
					node.put("thirdPartyId", money.getThirdPartyId());
					nodes.add(node);
				}
			}
		} catch (Exception ex) {
			nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.EXCEPTIONCODE);
			nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.EXCEPTIONMESSAGE);
			return nodeOne.toString();
		}
		nodeOne.put("lists", nodes);
		nodeOne.put("total", totalNumber.toString());
		nodeOne.put("curragePage", currentPageOne);
		nodeOne.put("pageSize", pageSizeOne);
		nodeOne.put(MessageAndCode.RESPONSECODE, MessageAndCode.SUCCESSCODE);
		nodeOne.put(MessageAndCode.RESPONSEMESSAGE, MessageAndCode.SUCCESSMESSAGE);
		return nodeOne.toString();
	}
	
	private ArrayNode fromStringToArrayNode(String source)
			throws JsonProcessingException, IOException{
		if(Utils.isEmpty(source)){
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode nodes = mapper.createArrayNode();
		JsonReader reader = new JsonReader();
		Object objectOne = reader.read(source);
		if(objectOne instanceof List){
			List<?> objectTwo = (List<?>)objectOne; 
			for(Object objectThree : objectTwo){
				if(objectThree instanceof Map){
					Map<?,?> objectFour = (Map<?,?>)objectThree;
					ObjectNode node = mapper.createObjectNode();
					for(Map.Entry<?, ?> entry: objectFour.entrySet()){
						if(entry.getKey() != null && entry.getValue() != null){
							node.put(entry.getKey().toString(), entry.getValue().toString());
						}
					}
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
}
