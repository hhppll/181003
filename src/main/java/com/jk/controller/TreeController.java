package com.jk.controller;

import com.jk.bean.Tree;
import com.jk.service.TreeService;
import com.jk.utils.FileUtil;
import com.jk.utils.ImportExcelUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TreeController {
    @Autowired
    private TreeService treeService;
    @RequestMapping("main")
    public String main() {
        
        return "main";
    }
    /**
     * @Author chh
     * @Description //TODO 查询
     * @Date 22:44 2019/5/13
     * @Param 
     * @return 
     **/
    @RequestMapping("findTreeList")
    @ResponseBody
    public List<Tree> findTreeList() {
        return treeService.findTreeList();
    }

    //导入  都是带着v的v
    @RequestMapping("enterPoi")
    @ResponseBody
    public Boolean enterPoi(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipart= (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("upfile");
        if(file.isEmpty()){
            throw new Exception("文件不存在");
        }
        InputStream in = file.getInputStream();
        List<List<Object>> bankListByExcel2 = new ImportExcelUtil().getBankListByExcel2(in, file.getOriginalFilename());
        System.out.println(bankListByExcel2);
        List<Tree> trees = new ArrayList<>();
        for (int i=0;i<bankListByExcel2.size();i++){
            List<Object> objects = bankListByExcel2.get(i);
            Tree tree = new Tree();
            tree.setText((String) objects.get(1));
            tree.setUrl((String) objects.get(2));
            tree.setPid(Integer.valueOf(objects.get(3).toString()));
            trees.add(tree);
        }

        try {
            treeService.savePoi(trees);
            in.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Author chh
     * @Description //TODO 导出
     * @Date 9:58 2019/5/14
     * @Param
     * String str="";
     *         for (int i=0; i<treeList.size();i++){
     *             str+=treeList.get(i).getId()+treeList.get(i).getText()+treeList.get(i).getUrl()+treeList.get(i).getPid();
     *         }
     * @return
     **/
    @ResponseBody
    @RequestMapping("exportPoi")
    public ResponseEntity<byte[]> exportExcel(String ids) throws IOException {
        List<Tree> treeList1 =treeService.findTreeListByIds(ids);
       String[] title={"id","text","url","pid"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
         for (int i=0;i<title.length;i++){
             cell = row.createCell(i);
             cell.setCellValue(title[i]);
         }
         for (int i=0; i<treeList1.size();i++){
             HSSFRow row1 = sheet.createRow(i+1);
             HSSFCell cell1 = row1.createCell(0);
             cell1.setCellValue(treeList1.get(i).getId());
             HSSFCell cell2 = row1.createCell(1);
             cell2.setCellValue(treeList1.get(i).getText());
             HSSFCell cell3 = row1.createCell(2);
             cell3.setCellValue(treeList1.get(i).getUrl());
             HSSFCell cell4 = row1.createCell(3);
             cell4.setCellValue(treeList1.get(i).getPid());
         }

        String pathname = "C:\\Users\\wo\\Desktop\\1.xlsx";
        File file = new File(pathname);
        file.createNewFile();
        FileOutputStream fileOutputStream = FileUtils.openOutputStream(file);
        workbook.write(fileOutputStream);
        return FileUtil.FileDownload(pathname);


       /* try {
            *//*Writer ww= new FileWriter("C:\\Users\\wo\\Desktop\\1.doc");
            ww.write(str);
            ww.close();*//*
            FileOutputStream fileOutputStream = FileUtils.openOutputStream(file);
              workbook.write(fileOutputStream);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }*/

    }



}
