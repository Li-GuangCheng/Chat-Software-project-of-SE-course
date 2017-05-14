package llj.testcode;

import java.util.Locale;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.translate.demo.TransApi;

public class TestCode {

	// 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20170514000047354";
    private static final String SECURITY_KEY = "povvc119uWhgvDB4YpIq";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Locale locale = Locale.getDefault();
		System.out.println(locale.getLanguage());
		System.out.println(locale.getCountry());
		try {
			TransApi api = new TransApi(APP_ID, SECURITY_KEY);

	        String query = "高度600米";
	        System.out.println(api.getTransResult(query, "auto", "en"));
	        try {
				JSONObject jsonObj = JSONObject.parseObject(api.getTransResult(query, "auto", "en"));
				JSONArray jsonArray = jsonObj.getJSONArray("trans_result");
				JSONObject result = jsonArray.getJSONObject(0);
				System.out.println(result.toJSONString());
				System.out.println(result.getString("dst")+"("+result.getString("src")+")");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
