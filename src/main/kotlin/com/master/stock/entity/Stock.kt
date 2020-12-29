package com.master.stock.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Stock(@Id var code:String="", var name:String="", var pinyin:String="", var lastClose:String="")