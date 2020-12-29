package com.master.stock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class StockTradeToolApplication

fun main(args: Array<String>) {
	runApplication<StockTradeToolApplication>(*args)
}
