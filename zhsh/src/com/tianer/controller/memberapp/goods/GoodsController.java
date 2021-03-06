package com.tianer.controller.memberapp.goods;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.html.HtmlUser;
import com.tianer.entity.zhihui.YouXuanShop;
import com.tianer.service.memberapp.AppGoodsService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppShopCarService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.service.memberapp.AppStorepc_marketingService;
import com.tianer.service.storepc.liangqin.shopmanage.CategoryManageService;
import com.tianer.service.storepc.store_marketingtype.Storepc_marketingtypeService;
import com.tianer.service.storepc.tableNumber.TablerNumberService;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.StringUtil;

/**
 * 
* 类名称：GoodsController   
* 类描述：   商品管理
* 创建人：魏汉文  
* 创建时间：2016年7月4日 下午12:52:29
 */
@Controller("appGoodsController")
@RequestMapping(value="/app_goods")
public class GoodsController extends BaseController {
	
	@Resource(name="appGoodsService")
	private AppGoodsService appGoodsService;
	
	@Resource(name = "tablerNumberService")
	private TablerNumberService tablerNumberService;
	
	@Resource(name="appOrderService")
	private AppOrderService appOrderService;
	
	 
	@Resource(name="appShopCarService")
	private AppShopCarService appShopCarService;
	
 	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	@Resource(name = "/categoryManageService")
	private CategoryManageService categoryManageService;

	
	/**
	 * 前往商店商品页面
	 * 魏汉文20160630
	 */
	@RequestMapping(value="goStoreGoods")
	@ResponseBody
	public Object goStoreGoods(){
 		Map<String,Object> map = new HashMap<String,Object>();
   		List<PageData> allList=new ArrayList<PageData>();//用来存储营销List
  		List<PageData> all =new ArrayList<PageData>();//用来存储营销List
		List<PageData> redList =new ArrayList<PageData>( );	//列出红包列表	
		List<PageData> varList =new ArrayList<PageData>( );	//列出红包列表	
    	String result = "1";
		String message=" 前往商店商品页面";
		PageData pd = new PageData();
		try{ 
				pd=this.getPageData();	
				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
						pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
  				}
//				//获取我的购物车内容
//				pd = this.getPageData();
//				pd.put("goods_type", "1");
//				List<PageData> shopList = shopCarService.shopCarList(pd);
//				for(PageData e :shopList){
//					double money=Double.parseDouble(e.getString("sale_money"))*Double.parseDouble(e.getString("goods_number"));
//					e.put("money", df.format(money));
//				}
//				map.put("shopList", shopList);
//				shopList=null;
				//判断是否还有符合的红包
				Map<String,Object> map1=TongYong.storeAndMemberByRed(pd);//包括会员id和商家id
				boolean flag=(boolean) map1.get("flag");
				varList=(List<PageData>) map1.get("varList");
				if(!flag){
//					 ("当前商家红包不存在");
				}else{
					 for(PageData e : varList){
 					 			PageData redpd=new PageData();
							    String choice_type=e.getString("choice_type");
							    String redpackage_type=e.getString("redpackage_type");
							    double money=Double.parseDouble(e.getString("money"));//总金钱
								double overget_money=Double.parseDouble(e.getString("overget_money"));//已领金钱
								int redpackage_number=Integer.parseInt(e.getString("redpackage_number"));//总红包
								int overget_number=Integer.parseInt(e.getString("overget_number"));//已领红包个数
								if(redpackage_number-overget_number==1){
											redpd.put("money", TongYong.df2.format(money-overget_money ));
											redpd.put("redpackage_content", e.getString("name"));
											redpd.put("store_redpackets_id", e.getString("store_redpackets_id"));
											redpd.put("redpackage_type", e.getString("type"));
											if(redpackage_type.equals("1")){
												redpd.put("redpackage_type_name", "元");
											}else if(redpackage_type.equals("2")){
												redpd.put("redpackage_type_name", "折");
											}
											redList.add(redpd);
								}else{
									if(redpackage_type.equals("1")){
											if(choice_type.equals(Const.redtwo_type)){//平均
												redpd.put("money", TongYong.df2.format(money/redpackage_number));
												redpd.put("redpackage_content", e.getString("name"));
												redpd.put("store_redpackets_id", e.getString("store_redpackets_id"));
												redpd.put("redpackage_type", redpackage_type);
												redpd.put("redpackage_type_name", "元");
												redList.add(redpd);
											}else if(choice_type.equals(Const.redone_type)){//随机
													double lessmoney=money-overget_money;
													int lessnumber=redpackage_number-overget_number;
													//获取平均值
													double pjmoney=lessmoney/lessnumber;
													double minpjmoney=pjmoney/2;
													double maxpjmoney=pjmoney/2+pjmoney;
													double suijimoney=StringUtil.getSuiJi(minpjmoney, maxpjmoney);
													redpd.put("money", TongYong.df2.format(suijimoney));
													redpd.put("redpackage_content", e.getString("name"));
													redpd.put("store_redpackets_id", e.getString("store_redpackets_id"));
													redpd.put("redpackage_type",redpackage_type);
													redpd.put("redpackage_type_name", "元");
													redList.add(redpd);
											}
									}else if(redpackage_type.equals("2")){
											redpd.put("money", e.getString("money"));
											redpd.put("redpackage_content", e.getString("name"));
											redpd.put("store_redpackets_id", e.getString("store_redpackets_id"));
											redpd.put("redpackage_type", redpackage_type);
											redpd.put("redpackage_type_name", "折");
											redList.add(redpd);
									}else if(redpackage_type.equals("51")){
										
									}else if(redpackage_type.equals("52")){
										
									}
							}
							redpd=null;
   					 }
	  			}
				PageData one=new PageData();
				one.put("name", Const.goodssort_one);
				one.put("sort", "1");
				one.put("id", "1");
				one.put("list", redList);
 				all.add(one);
 				one=null;
 				redList=null;
   				//获取今日特惠
				PageData two=new PageData();
				allList = appGoodsService.getYingXiaoGoods(pd); 
				two.put("name", Const.goodssort_two);
				two.put("sort", "2");
				two.put("id", "2");
				two.put("list", allList);
 				all.add(two);
 				two=null;
 				allList=null;
 				allList=new ArrayList<PageData>();//用来存储营销List
    			//人气版
				PageData three=new PageData();
				allList = appGoodsService.getRenQiGoods(pd); 
				three.put("name", Const.goodssort_three);
				three.put("sort", "3");
				three.put("id", "3");
				three.put("list", allList);
 				all.add(three);
 				three=null;
 				allList=null;
 				allList=new ArrayList<PageData>();//用来存储营销List
    			//所有分类
				allList = appGoodsService.listAllSort(pd);
				for(int i=0 ; i<allList.size() ;i++){
 					PageData e=allList.get(i);
 					if(e == null || e.equals("")){
 						allList.remove(i);
 						i=i-1;
 					}else{
 						String name=e.getString("name");
 	 					String sort=e.getString("sort");
 						e.put("store_id", pd.getString("store_id"));
 						List<PageData> goodsList = appGoodsService.listAllGoodsBySortId(e);	//指定分类的商品
 						PageData e2=new PageData();
 						e2.put("name",name);
 	 					e2.put("sort",Integer.parseInt(sort)+3+"");
 						e2.put("id",e.getString("goods_category_id"));
 						e2.put("list", goodsList);
 	 					all.add(e2);
 	 					e2=null;
 	 					goodsList=null;
 					}
    			}
  				allList=null;
   		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", all);
 		return map;
 	}
	
	
	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService appMember_redpacketsService;
	
 
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService appStore_redpacketsService;
	
	@Resource(name="appStorepc_marketingService")
	private AppStorepc_marketingService appStorepc_marketingService;
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	

	@Resource(name = "storepc_marketingtypeService")
	private Storepc_marketingtypeService storepcMarketingTypeService;
	
	
	/**
	 * 优惠买单，按总金额购买，商家ID，支付金钱,不优惠金额
	 *  
	 *  app_goods/allMoneyByOne.do
	 *  
	 *  paymoney 支付总金额
	 *  notmoney 不优惠金额
	 *  allgoods  购买商品拼接： 1.（ 商品id@数量@总金额，商品id@数量@总金额 ） 2.非商品购买的时候传“”空字符串
	 *  store_id 商家ID
	 *  member_id 会员ID
	 */
	@RequestMapping(value="/allMoneyByOne")
	@ResponseBody
	public Object allMoneyByOne(){
 		Map<String,Object> map = new HashMap<String,Object>();
  		String result = "1";
		String message="按总金额购买";
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
 				}
  				//============================
				pd.put("pay_sort_type", "1");
   				//营销开始：先判断折扣设置，折扣完的金额计算积分值，接下来是判断折扣后的金额是否满足红包及其它优惠条件；最后的金额是本次应买单的金额；
				String paymoney=pd.getString("paymoney");
				if(paymoney == null || paymoney.equals("") ){
					paymoney="0";
				}
				String notmoney=pd.getString("notmoney");
				if(notmoney == null || notmoney.equals("")){
					notmoney="0";
				}
				double youhui_money=Double.parseDouble(paymoney)-Double.parseDouble(notmoney);
				if(youhui_money < 0 || youhui_money < Double.parseDouble(notmoney) ){
					map.put("result", "0");
					map.put("message", "不优惠金额不能大总金额的50%");
					map.put("data", "");
					return map;
				}
				double notyouhui_money=Double.parseDouble(notmoney);
				//判断是否为购物车购买
				if(pd.getString("allgoods") != null && !pd.getString("allgoods").equals("")){
					//营销开始：先判断折扣设置，折扣完的金额计算积分值，接下来是判断折扣后的金额是否满足红包及其它优惠条件；最后的金额是本次应买单的金额；
	  				youhui_money=0;
	  				notyouhui_money=0;
	 				//处理allgoods
					List<PageData> goodsList=new ArrayList<PageData>();
					String[] goodsstr=pd.getString("allgoods").split(",");
					PageData goodspd=null;
					for (int i = 0; i < goodsstr.length; i++) {
	 					goodspd=new PageData();
						goodspd.put("goods_id", goodsstr[i].split("@")[0]);
						if(ServiceHelper.getAppGoodsService().findById(goodspd).getString("goods_type").equals("1")){//今日特价商品不参与优惠--相当于不优惠金额
							notyouhui_money+=Double.parseDouble(goodsstr[i].split("@")[2]);
						}else{
							youhui_money+=Double.parseDouble(goodsstr[i].split("@")[2]);
						}
						goodspd.put("goods_name", ServiceHelper.getAppGoodsService().findById(goodspd).getString("goods_name"));
						goodspd.put("shop_number", goodsstr[i].split("@")[1]);
						goodspd.put("summoney", goodsstr[i].split("@")[2]);
						goodsList.add(goodspd);
						goodspd=null;
					}
				}
 				//优惠买单信息
				Map<String,Object> yhmdpd=TongYong.youhuimaidan(pd,youhui_money,notyouhui_money);
				//--------------
				//获取改商家桌号
				List<PageData> tableNumberList = ServiceHelper.getTablerNumberService().listAll(pd);
				yhmdpd.put("tableNumberList", tableNumberList);
				//商家名称
				yhmdpd.put("store_name", ServiceHelper.getAppStoreService().findById(pd).getString("store_name"));
				map.put("data",yhmdpd );
				yhmdpd=null;
	 			tableNumberList=null;
     	} catch(Exception e){
    		map.put("data","");
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	  
	
	
	
	
	
	
	
	
	/**
	 * 优惠买单，按总类别购买，商家ID，支付金钱--不存在分类买单在优惠买单的情况下
 	 */
	@RequestMapping(value="/allMoneyByTwo")
	@ResponseBody
	public Object allMoneyByTwo(){
 		Map<String,Object> map = new HashMap<String,Object>();
  		String result = "1";
		String message="按总类购买";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			pd.put("pay_sort_type", "2");
 			//优惠买单信息
			Map<String,Object> yhmdpd=TongYong.youhuimaidan(pd,0,0);
 			//获取改商家桌号
			List<PageData> tableNumberList = ServiceHelper.getTablerNumberService().listAll(pd);
			yhmdpd.put("tableNumberList", tableNumberList);
			//商家名称
			yhmdpd.put("store_name", ServiceHelper.getAppStoreService().findById(pd).getString("store_name"));
			map.put("data",yhmdpd );
 			yhmdpd=null;
 			tableNumberList=null;
    	} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		pd=null;
		return map;
	}
	
	
	
	public static void main(String[] args){
	 
	}
	
  

	
  	 
	/**
	 * 新增/减少/删除   商品进购物车购物车数据---按+的时候
	 * goods_id,caozuo_number,member_id，goods_type  
	 */
	@RequestMapping(value="/caozuoShopCar")
	@ResponseBody
	public Object caozuoShopCar(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="操作成功";
		String nowkucun="0";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
  			}
 			String goods_type=pd.getString("goods_type");
			String member_id=pd.getString("member_id");
			String time=Const.youxuangoods_times;
			if(goods_type == null || goods_type.equals("")){
				goods_type="1";
			}
			if(appMemberService.findById(pd) == null){
					map.put("result", "0");
					map.put("message", "请先前往登录");
					map.put("data", "1");
					return map;
			}
			int caozuo_number=Integer.parseInt(pd.getString("caozuo_number"));
			//判断是否已经新增过该商品
			PageData e=appShopCarService.findById(pd);
			boolean flag=false;
			if(e == null){
 				flag=true;
   			}
			if(goods_type.equals("1")){//(caozuo_number:当前的数量：加减之后的数量)
				time=Const.goods_times;
				PageData goodspd=appGoodsService.findById(pd);
				if(goodspd != null){
					int stock_number=Integer.parseInt(goodspd.getString("stock_number"));//库存
					nowkucun=(stock_number-caozuo_number)+"";
// 	 				if(stock_number < caozuo_number){
//	 						map.put("result", "0");
//							map.put("message","库存不足,库存剩余"+stock_number);
//							map.put("data",stock_number+"");
//							pd=null;
//							return map;
// 					}else{
// 						int shop_number=0;
 						if(flag && caozuo_number >0){
 							pd.put("goods_name", goodspd.getString("goods_name"));
 	 						pd.put("sale_money", goodspd.getString("retail_money"));
 	 						pd.put("store_id", goodspd.getString("store_id"));
 	 						pd.put("goods_type", "1");
 	 						pd.put("goods_number", caozuo_number);
 	 						pd.put("shopcart_id", BaseController.getTimeID());	//主键
 	 						appShopCarService.saveShop(pd);
// 	 						shop_number=caozuo_number;
 						}else if(!flag){
// 							shop_number=Integer.parseInt(e.getString("goods_number"));
  			   					if(caozuo_number <= 0){
  			   					appShopCarService.delShop(e);
 			 				}else{
  			  					pd.put("goods_number",  caozuo_number+"");
  			  					appShopCarService.updateshop(pd);
 			 				}
  						} 
// 						if(shop_number != 0){
// 							//减少库存
// 	 						goodspd.put("goods_number", -caozuo_number+"");
// 	 						appGoodsService.updateGoodsBuyNumber(goodspd);
// 						}
//   				}
 				}
				goodspd=null;
			}else if(goods_type.equals("2")){//(caozuo_number:加减的数量)
				pd.put("youxuangg_id", pd.getString("goods_id"));
				PageData goodspd=ServiceHelper.getYouXuanService().finddetailgg(pd);
 				if(goodspd != null){
 					//线判断当前用户限购
 					int needsale_number=Integer.parseInt(goodspd.getString("needsale_number"));//库存
 					nowkucun=(needsale_number-caozuo_number)+"";
 					String isxiangou=goodspd.getString("isxiangou");
 					if(goodspd.getString("xiangou_number") == null || goodspd.getString("xiangou_number").equals("")){
 						goodspd.put("xiangou_number", "100");
 					}
 					int xiangou_number=Integer.parseInt(goodspd.getString("xiangou_number"));
  					if(isxiangou.equals("1")){
 						int havebuy=0;
 						//订单中
 						if(ServiceHelper.getAppOrderService().getOrderNumberByGoods(pd) != null){
 							havebuy=(int)(Double.parseDouble(ServiceHelper.getAppOrderService().getOrderNumberByGoods(pd).getString("shop_number")));
 						}
 						if(!flag){
 							havebuy+=Integer.parseInt(e.getString("goods_number"));
  						}
 						if(havebuy+caozuo_number > xiangou_number){
 							map.put("result", "0");
 							map.put("message","当前商品为限购商品，你已购满"+xiangou_number+"件");
 							map.put("data",needsale_number+"");
 							pd=null;
 							return map;
 						}
 					}
 		 			if((needsale_number <   caozuo_number) || (needsale_number == 0 && caozuo_number > 0) ){
 		 					map.put("result", "0");
							map.put("message","库存不足");
							map.put("data",needsale_number+"");
							pd=null;
							return map;
 					}else{
 						int shop_number=0;
 						if(flag && caozuo_number >0){
 							pd.put("goods_number", caozuo_number);
 							pd.put("goods_name", goodspd.getString("goods_name"));
 	 						pd.put("sale_money", goodspd.getString("sale_money"));
 	 						pd.put("store_id", goodspd.getString("store_id"));
 	 						pd.put("goods_type", "2");
 	 						pd.put("shopcart_id", BaseController.getTimeID());	//主键
 	 						appShopCarService.saveShop(pd);
 	 						shop_number=caozuo_number;
 						}else if(!flag){
 							shop_number=Integer.parseInt(e.getString("goods_number"));
 							int goods_number=shop_number+caozuo_number;
	 			   			if(goods_number <= 0){
	 			   				appShopCarService.delShop(e);
 			 					message="删除成功";
 			 				}else{
	 			  				pd.put("goods_number",  goods_number+"");
	 			  				appShopCarService.updateshop(pd);
 			 				}
 						}
 						if(shop_number != 0){
 							//减少库存
	 						goodspd.put("goods_number", -caozuo_number+"");
		 					ServiceHelper.getYouXuanService().updateYouXuanGoodsBuyNumber(goodspd);
		 					//判断库存改状态
			 				  PageData goodsggpd=ServiceHelper.getYouXuanService().finddetailgg(goodspd);
			 				  if(ServiceHelper.getYouXuanService().lesssale_number(goodsggpd) == 0){
			 						PageData xpd=new PageData();
			 						xpd.put("goods_status", "98");
			 						xpd.put("youxuangoods_id", goodsggpd.getString("youxuangoods_id"));
			 						ServiceHelper.getYouXuanService().editGoods(xpd);
			 				  }else{
			 					  	PageData xpd=new PageData();
			 						xpd.put("goods_status", "4");
			 						xpd.put("youxuangoods_id", goodsggpd.getString("youxuangoods_id"));
			 						ServiceHelper.getYouXuanService().editGoods(xpd);
			 				  }
 						} 
 	 				}
   				}else{
  					result="0";
  					message="商品不存在";
  				}
				goodspd=null;
			}
			if(result.equals("1")){
				//设置定时器 
				long l=(long) (Double.parseDouble(time)*60*60*1000);
		 		YouXuanShop ys=new YouXuanShop(member_id,goods_type);
				Timer tt=new Timer();
				tt.schedule(ys, l);
			}
 			e=null;
		} catch(Exception e){
			result="0";
			message="获取异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data",nowkucun);
		pd=null;
		return map;
	}
 
	
	/**
	 *  扫一扫优惠买单渠道进入购买
 	 *  app_goods/saoYiSaoShopBuyGoods.do
 	 *  
	 *  allmoney  支付总金额
	 *  notmoney  不优惠金额
 	 *  store_id  商家ID
	 *  member_id 会员ID
	 *  redpackage_id  红包ID
	 */
	@RequestMapping(value="/saoYiSaoShopBuyGoods")
	@ResponseBody
	public Object SaoYiSaoShopBuyGoods(){
 		Map<String,Object> map = new HashMap<String,Object>();
  		String result = "1";
		String message="扫一扫优惠买单购买";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			pd.put("pay_sort_type", "1");
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
 			}
 			String allmoney=pd.getString("allmoney");
 			if(allmoney == null || allmoney.equals("") ){
 				allmoney="0";
			}
			String notmoney=pd.getString("notmoney");
			if(notmoney == null || notmoney.equals("")){
				notmoney="0";
			}
			double youhui_money=Double.parseDouble(allmoney)-Double.parseDouble(notmoney);
			if(youhui_money < 0 || youhui_money < Double.parseDouble(notmoney) ){
				map.put("result", "0");
				map.put("message", "不优惠金额不能大总金额的50%");
				map.put("data", "");
				return map;
			}
 			//优惠买单信息
			Map<String,Object> yhmdpd=TongYong.YouHuiMaiDanByTwoForMember(pd, youhui_money, Double.parseDouble(notmoney));
 			map.put("data",yhmdpd );
 			this.getRequest().getSession().removeAttribute(pd.getString("member_id")+"canUseList");
			this.getRequest().getSession().removeAttribute(pd.getString("member_id")+"notUseList");
 			PageData useRedPd = (PageData) yhmdpd.get("useRedPd");
 			this.getRequest().getSession().setAttribute(pd.getString("member_id")+"canUseList", (List<PageData>) useRedPd.get("canUseList"));
 			this.getRequest().getSession().setAttribute(pd.getString("member_id")+"notUseList", (List<PageData>) useRedPd.get("notUseList"));
  			yhmdpd=null;
  			useRedPd=null;
			yhmdpd=null;
		} catch(Exception e){
    		map.put("data","");
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
	/**
	 *  通过购物车进入渠道
 	 *  app_goods/catShopBuyGoods.do
 	 *  
	 *  allmoney  支付总金额（===可传可不传======）
 	 *  allgoods  购买商品拼接： 1.（ 商品id@数量@总金额，商品id@数量@总金额 ） 2.非商品购买的时候传“”空字符串
	 *  store_id  商家ID
	 *  member_id 会员ID
	 *  redpackage_id 红包ID
	 *  
	 */
	@RequestMapping(value="/catShopBuyGoods")
	@ResponseBody
	public Object CatShopBuyGoods(){
 		Map<String,Object> map = new HashMap<String,Object>();
  		String result = "1";
		String message="按购物车购买";
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
 				}
    			//营销开始：先判断折扣设置，折扣完的金额计算积分值，接下来是判断折扣后的金额是否满足红包及其它优惠条件；最后的金额是本次应买单的金额；
  				double youhui_money=0;
 				double notyouhui_money=0;
 				//处理allgoods
				List<PageData> goodsList=new ArrayList<PageData>();
				String[] goodsstr=pd.getString("allgoods").split(",");
				PageData goodspd=null;
				for (int i = 0; i < goodsstr.length; i++) {
 					goodspd=new PageData();
					goodspd.put("goods_id", goodsstr[i].split("@")[0]);
					if(ServiceHelper.getAppGoodsService().findById(goodspd).getString("goods_type").equals("1")){//今日特价商品不参与优惠--相当于不优惠金额
						notyouhui_money+=Double.parseDouble(goodsstr[i].split("@")[2]);
					}else{
						youhui_money+=Double.parseDouble(goodsstr[i].split("@")[2]);
					}
					goodspd.put("goods_name", ServiceHelper.getAppGoodsService().findById(goodspd).getString("goods_name"));
					goodspd.put("shop_number", goodsstr[i].split("@")[1]);
					goodspd.put("summoney", goodsstr[i].split("@")[2]);
					goodsList.add(goodspd);
					goodspd=null;
				}
   				//优惠买单信息
				Map<String,Object> yhmdpd=TongYong.YouHuiMaiDanByTwoForMember(pd, youhui_money, notyouhui_money);
   				yhmdpd.put("goodsList", goodsList);
				map.put("data",yhmdpd );
				yhmdpd=null;
      	} catch(Exception e){
    		map.put("data","");
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	 
	
	
	
}
