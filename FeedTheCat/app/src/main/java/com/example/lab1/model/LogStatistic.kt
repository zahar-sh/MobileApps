package com.example.lab1.model

class LogStatistic {
    var id : Int = 0;
    var time : String = ""
    var satiety : Int = 0

    constructor(satiety:Int, time:String) {
        this.satiety = satiety
        this.time = time
    }
}