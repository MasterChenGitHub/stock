package com.master.stock.controller

import com.master.stock.util.ImportStockUtil.Companion.extractExcelSz
import com.master.stock.entity.Stock
import com.master.stock.repository.StockRepository
import com.master.stock.response.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
@RequestMapping("/stock")
class StockController {

    @Autowired
    private lateinit var stockRepository: StockRepository

    @GetMapping("/all")
    fun fetchCloseValues(): ResponseEntity<BaseResponse<List<Stock>>>
    {
//        initData()
        var res = BaseResponse<List<Stock>>()
        var s= stockRepository.findAll().toList()

        return if ( s.isNotEmpty()) {
            res.success()
            res.data = s
            ResponseEntity(res, HttpStatus.OK)
        } else {
            res.notfound()
            ResponseEntity(res, HttpStatus.NOT_FOUND)
        }

}
    fun initData() {
        for (stock in extractExcelSz()) {
            stockRepository.save(stock)
        }

    }

    @GetMapping("/optionalStock")
    fun optionalStock(@RequestParam("stock") id: String): ResponseEntity<BaseResponse<List<Stock>>> {
        var res = BaseResponse<List<Stock>>()
        var s = stockRepository.findByCodeOrNameOrPinyin(id)

        return if (s.isPresent) {
            res.success()
            res.data=s.get()

            ResponseEntity(res, HttpStatus.OK)
        } else {
            res.notfound()
            ResponseEntity(res, HttpStatus.NOT_FOUND)
        }
    }

}