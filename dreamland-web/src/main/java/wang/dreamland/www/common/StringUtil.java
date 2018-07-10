package wang.dreamland.www.common;


/**
 * Created by wly on 2018/3/6.
 */
public class StringUtil {
    public static String getString(String str,Long id){
            String[] arr = str.split( "," );
            String s = "";
            if(arr!=null && arr.length>0){
                for(int i = 0; i<arr.length;i++){
                    System.out.println(id.toString().equals( arr[i] ));
                    if(id.toString().equals( arr[i] )){
                        System.out.println(arr[i]);
                        continue;
                    }else {
                        if(i == arr.length - 1){
                            s = s + arr[i] ;
                        }else {
                            s = s + arr[i] + ",";
                        }
                    }


                }
            }
        if (s.endsWith( "," )){
                s = s.substring( 0,s.length()-1 );
        }
        return s;
    }
}
