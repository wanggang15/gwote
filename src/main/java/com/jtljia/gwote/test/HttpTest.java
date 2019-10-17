package com.jtljia.gwote.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.util.HttpUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpTest {

    class InnerTestClass{
        private String strVar = "String";
        private int intVar = 1;
        private Map<String,Object> mapVar = new HashMap<>();

        InnerTestClass(){
            mapVar.put("key","value");
        }
    }

    @Test
    public void testPost() {
        JSONObject param = new JSONObject();
        param.put("phoneNumber", "18235111011");
        param.put("password", "123456");
        System.out.println(param.toString());
        String result = HttpUtil.doPost("http://localhost:8888/psms/login", param.toString());
        System.out.println(result);
    }

    @Test
    public void ToUpperCase(){
        String str = "pthyjhdsq";
        String upperCase = str.toUpperCase();
        System.out.println("DS-"+upperCase);
    }

    @Test
    public void test1(){
        String str = "{\"head\":{\"state\":\"success\",\"code\":0},\"body\":[{\"orgName\":\"南通通州店\",\"orgId\":\"879ba0ce52fd4252a09d281f3fb955c3\"},{\"orgName\":\"亳州店\",\"orgId\":\"da98a6de86c341c3869b2234403a06f3\"},{\"orgName\":\"威海店\",\"orgId\":\"3ea7771cdbe64626a5bdedbd49dfcf09\"},{\"orgName\":\"湖州店\",\"orgId\":\"7be8dd11d29444dba28c4a916bb15f7c\"},{\"orgName\":\"运城店\",\"orgId\":\"50d8c55b49d74dfba8136b73bab6da12\"},{\"orgName\":\"赣州店\",\"orgId\":\"2fda474ee17b4ff29332c23eda5a8416\"},{\"orgName\":\"南宁店\",\"orgId\":\"d6b66b9d453a4bbfac374c03dde25d21\"},{\"orgName\":\"包头店\",\"orgId\":\"0066f9bed9d24241a21ae01a7ba9714b\"},{\"orgName\":\"呼和浩特店\",\"orgId\":\"466c444055fb447eb6e0d6f437973b97\"},{\"orgName\":\"嘉善店\",\"orgId\":\"d5c594638d714bf5be0a2e4141ecc049\"},{\"orgName\":\"重庆万州店\",\"orgId\":\"0eea5dabd0da44b4955b479fdc3e6f68\"},{\"orgName\":\"九江店\",\"orgId\":\"85afd3fd8e7e4f799bd06925d418aae0\"},{\"orgName\":\"宜春店\",\"orgId\":\"66972c5080cf4f569df651e8a47ca245\"},{\"orgName\":\"长治店\",\"orgId\":\"58ec5dc7f49543bb9186259a41c69e78\"},{\"orgName\":\"重庆开州店\",\"orgId\":\"df84b9a66259416c9849125e3a3f3fbc\"},{\"orgName\":\"泰州高港店\",\"orgId\":\"1b7366d5d5054716a64dc8b2961b3a8b\"},{\"orgName\":\"兴化店\",\"orgId\":\"fbee6243acbe4aaaa186908331f7287b\"},{\"orgName\":\"重庆江北店\",\"orgId\":\"a0e92167f0644f79af7cb9260a039549\"},{\"orgName\":\"六安店\",\"orgId\":\"6493c6d72fda4fb096d53644e2fdb5aa\"},{\"orgName\":\"南充店\",\"orgId\":\"61480c0578fa4586aa813a4b5ae32d78\"},{\"orgName\":\"武汉武昌店\",\"orgId\":\"dca5d86dcaba48dab1c292ac515a4d7b\"},{\"orgName\":\"自贡店\",\"orgId\":\"291f8e63c7734163ba100866d7c065e0\"},{\"orgName\":\"安康店\",\"orgId\":\"be5f43d6d4af44a18000772d10b71f3f\"},{\"orgName\":\"濮阳店\",\"orgId\":\"6c2c2f442f07492f8c2f6b2dc2547b37\"},{\"orgName\":\"如皋店\",\"orgId\":\"2aeacfed3b344c01ba36d9b2e3529f0d\"},{\"orgName\":\"淮安金湖店\",\"orgId\":\"ba9813cb3e2746afa48d4315f197aa40\"},{\"orgName\":\"安吉店\",\"orgId\":\"d9b900554c8e481a9bc9ad4a9d3435cc\"},{\"orgName\":\"大连店\",\"orgId\":\"5270c84ac5da44c28e72a370dc0f603d\"},{\"orgName\":\"石家庄店\",\"orgId\":\"a4285da8ed6a4bf391df7e477bdbb211\"},{\"orgName\":\"南昌店\",\"orgId\":\"c447fdd07deb45479e0321a136e0c9af\"},{\"orgName\":\"嘉兴店\",\"orgId\":\"4bdb69a5d1fe4aadaed7264ba84dcbce\"},{\"orgName\":\"泰州店\",\"orgId\":\"6a65f53bc6e144a8a54552aea8592a75\"},{\"orgName\":\"江阴店\",\"orgId\":\"f7a4c2da4df944af88174e87d32e4104\"},{\"orgName\":\"镇江店\",\"orgId\":\"0af4688537fb4d7881c1b9d6c4f94546\"},{\"orgName\":\"南通店\",\"orgId\":\"674dabb2e93f4b9ab2a0c54822532be9\"},{\"orgName\":\"汉中店\",\"orgId\":\"a8b9da9604ea4ea3bb958c0b9900686a\"},{\"orgName\":\"三明店\",\"orgId\":\"4060eafbe6594ffaa9afd6285bc9a801\"},{\"orgName\":\"泉州店\",\"orgId\":\"9fc0b2cb9933491d82665c5a6be18e5d\"},{\"orgName\":\"厦门店\",\"orgId\":\"bddbf36c6eba49668a8e91ebfa9924d1\"},{\"orgName\":\"丰县店\",\"orgId\":\"79193fd078ed4b12b4438673a720f28e\"},{\"orgName\":\"宜兴店\",\"orgId\":\"de8f0f5c9c9448738f5c051b78e08272\"},{\"orgName\":\"天津店\",\"orgId\":\"9c2bd44d32ab4872b245c4266bbe4704\"},{\"orgName\":\"烟台店\",\"orgId\":\"e5ac580f6ea4451ea1902be0989058cc\"},{\"orgName\":\"太仓店\",\"orgId\":\"54bc364eda794b9abf4034df05adb8d7\"},{\"orgName\":\"淮安店\",\"orgId\":\"b783c6c0fc4f46d6b91d4e6185e0dc62\"},{\"orgName\":\"日照店\",\"orgId\":\"c46d06fe57aa4df2a2b26986b4e4f8d3\"},{\"orgName\":\"如东店\",\"orgId\":\"d1af8127967c4db0b29fb3af76adeca0\"},{\"orgName\":\"海安店\",\"orgId\":\"0f8c31b370e24d43a38f8d5311059fc1\"},{\"orgName\":\"海门店\",\"orgId\":\"60b0e698703d49949d1aea8ba2de6f26\"},{\"orgName\":\"启东店\",\"orgId\":\"61c34c2cff0048b3ae9305518d74a81b\"},{\"orgName\":\"东营店\",\"orgId\":\"7fe2de2770c742ffa2f364249e64ebcd\"},{\"orgName\":\"武汉汉口店\",\"orgId\":\"d55d6d202fe7460fb32ac69afc1f249c\"},{\"orgName\":\"阜阳店\",\"orgId\":\"d8f32100d15649cf9bf6dd410c410738\"},{\"orgName\":\"重庆渝北店\",\"orgId\":\"7bc5458d62ca473e85551afabf7386ce\"},{\"orgName\":\"重庆永川店\",\"orgId\":\"3ee0b54efdad4ee3947219ad73206ec3\"}],\"success\":true}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONArray body = jsonObject.getJSONArray("body");
        System.out.println(body.size());
    }

    @Test
    public void test2(){
        InnerTestClass innerTestClass = new InnerTestClass();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1",innerTestClass);
        System.out.println(jsonObject.toString());
    }

    @Test
    public void insertSort(){
        int[] array = new int[]{4,7,2,1,3,8,10};
        for (int i =1;i<array.length-1;i++){
            for (int j = 0 ;j <i;j++){
                int pre = array[i];
                int aft = array[j];
                if (pre<aft){
                    pre = pre^aft;
                    aft = pre^aft;
                    pre = pre^aft;
                }
                array[i] = pre;
                array[j] = aft;
            }
        }
        for (int i : array) {
            System.out.println(i);
        }
    }

    @Test
    public void selectSort(){
        int[] array = new int[]{4,7,2,1,3,8,10};
        for (int i =1;i<array.length-1;i++){
            for (int j = 0 ;j <i;j++){
                int pre = array[i];
                int aft = array[j];
                if (pre<aft){
                    pre = pre^aft;
                    aft = pre^aft;
                    pre = pre^aft;
                }
                array[i] = pre;
                array[j] = aft;
            }
        }
        for (int i : array) {
            System.out.println(i);
        }
    }
}
