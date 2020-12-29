package com.master.stock.util


import com.master.stock.util.ChineseCharacterUtil
import com.master.stock.entity.Stock
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

class ImportStockUtil {


    companion object {

        fun extractExcelSh(): List<Stock> {
            var stoks = ArrayList<Stock>()

            val excelFilePath = "D:\\spring\\stock\\sh.xlsx"
            val inputStream = FileInputStream(File(excelFilePath))

            val workbook: Workbook = XSSFWorkbook(inputStream)
            val firstSheet: Sheet = workbook.getSheetAt(0)
            val iterator: Iterator<Row> = firstSheet.iterator()

            while (iterator.hasNext()) {
                val nextRow: Row = iterator.next()
                val cellIterator: Iterator<Cell> = nextRow.cellIterator()
                val code = cellIterator.next().numericCellValue.toInt()
                val name = cellIterator.next().stringCellValue
                val pinyin = ChineseCharacterUtil.convertHanzi2Pinyin(name, false)

                stoks.add(Stock("sh" + code.toString(), name, pinyin, ""))
//                print(code)
//                print(name)

                println()
            }

            workbook.close()
            inputStream.close()

            return stoks
        }

        fun extractExcelSz(): List<Stock> {
            var stoks = ArrayList<Stock>()

            val excelFilePath = "D:\\spring\\stock\\sz.xlsx"
            val inputStream = FileInputStream(File(excelFilePath))

            val workbook: Workbook = XSSFWorkbook(inputStream)
            val firstSheet: Sheet = workbook.getSheetAt(0)
            val iterator: Iterator<Row> = firstSheet.iterator()

            while (iterator.hasNext()) {
                val nextRow: Row = iterator.next()
                val cellIterator: Iterator<Cell> = nextRow.cellIterator()
                cellIterator.next()
                cellIterator.next()
                cellIterator.next()
                cellIterator.next()
                val code = cellIterator.next().stringCellValue
                val name = cellIterator.next().stringCellValue
                val pinyin = ChineseCharacterUtil.convertHanzi2Pinyin(name, false)

                stoks.add(Stock("sz" + code.toString(), name, pinyin, ""))
//                print(code)
//                print(name)

                println()
            }

            workbook.close()
            inputStream.close()

            print(stoks)
            return stoks
        }
    }
}

//fun main(arg: Array<String>) {
////    extractExcelSh()
//    extractExcelSz()
//}

