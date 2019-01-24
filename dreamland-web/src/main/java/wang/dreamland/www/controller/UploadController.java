package wang.dreamland.www.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.io.ByteStreams;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import wang.dreamland.www.common.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wly on 2018/5/20.
 */
@Controller
public class UploadController {
    private final static Logger log = Logger.getLogger(UploadController.class);

     @RequestMapping(value = "/fileUpload")
     @ResponseBody
      public Map<String, Object> fileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
             FileUploadException {
                 ServletContext application = request.getSession().getServletContext();
                 String savePath = Constants.SERVER_FILE_ROOT;
               // String savePath = application.getRealPath("/") + "images/";

                // String saveUrl = request.getContextPath() + "/images/";
                String saveUrl = Constants.DREAMLAND_DOMAIN + "/images/";
                 //上传图片，保存路径为：G:\wly\dreamland\target\dreamland\images/,文件url为：/images/
                 log.info( "上传图片，保存路径为："+savePath+",文件url为："+saveUrl );

                 // 定义允许上传的文件扩展名
                 HashMap<String, String> extMap = new HashMap<String, String>();
                 extMap.put("image", "gif,jpg,jpeg,png,bmp");
                 extMap.put("flash", "swf,flv");
                 extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
                 extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

                // 最大文件大小
                 long maxSize = 1000000;

                 response.setContentType("text/html; charset=UTF-8");

                 if (!ServletFileUpload.isMultipartContent(request)) {
                         return getError("请选择文件。");
                     }
                 // 检查目录
                 File uploadDir = new File(savePath);
                 if (!uploadDir.isDirectory()) {
                         return getError("上传目录不存在。");
                     }
                 // 检查目录写权限
                 if (!uploadDir.canWrite()) {
                         return getError("上传目录没有写权限。");
                     }

                 String dirName = request.getParameter("dir");
                 if (dirName == null) {
                         dirName = "image";
                     }
                 if (!extMap.containsKey(dirName)) {
                         return getError("目录名不正确。");
                     }
                 // 创建文件夹
                 savePath += dirName + "/";
                 saveUrl += dirName + "/";
                 File saveDirFile = new File(savePath);
                if (!saveDirFile.exists()) {
                         saveDirFile.mkdirs();
                     }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                 String ymd = sdf.format(new Date());
                 savePath += ymd + "/";
                 saveUrl += ymd + "/";
                 File dirFile = new File(savePath);
                 if (!dirFile.exists()) {
                         dirFile.mkdirs();
                     }

                 FileItemFactory factory = new DiskFileItemFactory();
                 ServletFileUpload upload = new ServletFileUpload(factory);
                 upload.setHeaderEncoding("UTF-8");


                 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

                 Iterator item = multipartRequest.getFileNames();
                 while (item.hasNext()) {

                         String fileName = (String) item.next();

                         MultipartFile file = multipartRequest.getFile(fileName);


                     // 检查文件大小

                         if (file.getSize() > maxSize) {

                                 return getError("上传文件大小超过限制。");

                             }

                         // 检查扩展名

                        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();

                         if (!Arrays. asList(extMap.get(dirName).split(",")).contains(fileExt)) {
                                 return getError("上传文件扩展名是不允许的扩展名。\n只允许"
                                                 + extMap.get(dirName) + "格式。");

                             }
                         SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

                         String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;

                        try {

                             File uploadedFile = new File(savePath, newFileName);

                             ByteStreams.copy(file.getInputStream(), new FileOutputStream(uploadedFile));

                            } catch (Exception e) {
                                return getError("上传文件失败。");

                             }
                         Map<String, Object> msg = new HashMap<String, Object>();
                         msg.put("success", 1);
                         msg.put("url", saveUrl + newFileName);
                        // =======savePath=======G:\wly\dreamland\target\dreamland\images/image/20180108/=====url======/images/image/20180108/20180108180524_256.png
                         log.info( "=======savePath======="+savePath+"=====url======"+saveUrl+newFileName );
                         return msg;
                         }
                 return null;
             }

             private Map<String, Object> getError(String message) {
                 Map<String, Object> msg = new HashMap<String, Object>();
                 msg.put("success",0);//0失败
                 msg.put("message", message);
                return msg;
             }

    @RequestMapping(value = "/fileManager")
    public void fileManager(HttpServletRequest request,
             HttpServletResponse response) throws ServletException, IOException {
                 ServletContext application = request.getSession().getServletContext();
                 ServletOutputStream out = response.getOutputStream();
                 // 根目录路径，可以指定绝对路径，比如 /var/www/attached/
                 String rootPath = application.getRealPath("/") + "images/";
                 // 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
                 String rootUrl = request.getContextPath() + "/images/";
                 // 图片扩展名
                 String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };

                 String dirName = request.getParameter("dir");
                 if (dirName != null) {
                         if (!Arrays.<String> asList(
                                         new String[] { "image", "flash", "media", "file" })
                                 .contains(dirName)) {
                                 out.println("Invalid Directory name.");
                                 return;
                             }
                         rootPath += dirName + "/";
                         rootUrl += dirName + "/";
                         File saveDirFile = new File(rootPath);
                         if (!saveDirFile.exists()) {
                                 saveDirFile.mkdirs();
                             }
                     }
                 // 根据path参数，设置各路径和URL
                 String path = request.getParameter("path") != null ? request
                         .getParameter("path") : "";
                 String currentPath = rootPath + path;
                 String currentUrl = rootUrl + path;
                 String currentDirPath = path;
                 String moveupDirPath = "";
                 if (!"".equals(path)) {
                         String str = currentDirPath.substring(0,
                                         currentDirPath.length() - 1);
                        moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0,
                                         str.lastIndexOf("/") + 1) : "";
                     }

                 // 排序形式，name or size or type
                 String order = request.getParameter("order") != null ? request
                         .getParameter("order").toLowerCase() : "name";

                 // 不允许使用..移动到上一级目录
                 if (path.indexOf("..") >= 0) {
                         out.println("Access is not allowed.");
                         return;
                     }
                 // 最后一个字符不是/
                 if (!"".equals(path) && !path.endsWith("/")) {
                         out.println("Parameter is not valid.");
                         return;
                     }
                 // 目录不存在或不是目录
                 File currentPathFile = new File(currentPath);
                 if (!currentPathFile.isDirectory()) {
                         out.println("Directory does not exist.");
                         return;
                     }
                 // 遍历目录取的文件信息
                List<Hashtable> fileList = new ArrayList<Hashtable>();
                 if (currentPathFile.listFiles() != null) {
                         for (File file : currentPathFile.listFiles()) {
                                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                                 String fileName = file.getName();
                                 if (file.isDirectory()) {
                                         hash.put("is_dir", true);
                                         hash.put("has_file", (file.listFiles() != null));
                                         hash.put("filesize", 0L);
                                        hash.put("is_photo", false);
                                        hash.put("filetype", "");
                                     } else if (file.isFile()) {
                                        String fileExt = fileName.substring(
                                                         fileName.lastIndexOf(".") + 1).toLowerCase();
                                       hash.put("is_dir", false);
                                        hash.put("has_file", false);
                                         hash.put("filesize", file.length());
                                         hash.put("is_photo", Arrays.<String> asList(fileTypes)
                                                       .contains(fileExt));
                                        hash.put("filetype", fileExt);
                                    }
                              hash.put("filename", fileName);
                                hash.put("datetime",
                                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file
                                                        .lastModified()));
                                 fileList.add(hash);
                            }
                    }

                 if ("size".equals(order)) {
                        Collections.sort(fileList, new SizeComparator());
                     } else if ("type".equals(order)) {
                        Collections.sort(fileList, new TypeComparator());
                     } else {
                         Collections.sort(fileList, new NameComparator());
                     }
                Map<String, Object> msg = new HashMap<String, Object>();
                 msg.put("moveup_dir_path", moveupDirPath);
                msg.put("current_dir_path", currentDirPath);
                 msg.put("current_url", currentUrl);
                msg.put("total_count", fileList.size());
                 msg.put("file_list", fileList);
                response.setContentType("application/json; charset=UTF-8");
                 String msgStr = JSON.toJSONString(msg);
                 out.println(msgStr);
             }

            class NameComparator implements Comparator {
         public int compare(Object a, Object b) {
                         Hashtable hashA = (Hashtable) a;
                         Hashtable hashB = (Hashtable) b;
                        if (((Boolean) hashA.get("is_dir"))
                                 && !((Boolean) hashB.get("is_dir"))) {
                                return -1;
                             } else if (!((Boolean) hashA.get("is_dir"))
                                 && ((Boolean) hashB.get("is_dir"))) {
                                 return 1;
                             } else {
                                 return ((String) hashA.get("filename"))
                                        .compareTo((String) hashB.get("filename"));
                            }
                     }
     }

         class SizeComparator implements Comparator {
         public int compare(Object a, Object b) {
                         Hashtable hashA = (Hashtable) a;
                        Hashtable hashB = (Hashtable) b;
                        if (((Boolean) hashA.get("is_dir"))
                                && !((Boolean) hashB.get("is_dir"))) {
                                 return -1;
                            } else if (!((Boolean) hashA.get("is_dir"))
                                 && ((Boolean) hashB.get("is_dir"))) {
                              return 1;
                             } else {
                                if (((Long) hashA.get("filesize")) > ((Long) hashB
                                        .get("filesize"))) {
                                         return 1;
                                    } else if (((Long) hashA.get("filesize")) < ((Long) hashB
                                         .get("filesize"))) {
                                         return -1;
                                   } else {
                                         return 0;
                                    }
                             }
                    }
     }

    class TypeComparator implements Comparator {
     public int compare(Object a, Object b) {
                         Hashtable hashA = (Hashtable) a;
                         Hashtable hashB = (Hashtable) b;
                         if (((Boolean) hashA.get("is_dir"))
                                 && !((Boolean) hashB.get("is_dir"))) {
                                 return -1;
                             } else if (!((Boolean) hashA.get("is_dir"))
                                 && ((Boolean) hashB.get("is_dir"))) {
                                 return 1;
                             } else {
                                 return ((String) hashA.get("filetype"))
                                         .compareTo((String) hashB.get("filetype"));
                             }
                     }
    }

 }
