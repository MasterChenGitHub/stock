package com.master.stock.repository

import com.master.stock.entity.Stock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface StockRepository : CrudRepository<Stock, String> {

    @Query("from Stock s where s.code like %:param% or s.name like %:param% or s.pinyin like %:param%")
    fun findByCodeOrNameOrPinyin(param: String):Optional<List<Stock>>

}