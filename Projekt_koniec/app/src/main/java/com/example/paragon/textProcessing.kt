package com.example.paragon


import com.google.mlkit.vision.text.Text
import kotlin.math.round

val prize = ArrayList<String>()
val name = ArrayList<String>()

fun textProcessing(visionText: Text?) : String {

    var text = ""
    var minLeft = 100000
    var maxRight = 0
    prize.clear()
    name.clear()

    if(visionText != null){
        val recognizedElements = ArrayList<Text.Element>()

        //maxRight i minLeft
        for (block in visionText.textBlocks) {
            for (line in block.lines) {
                for (element in line.elements) {
                    recognizedElements.add(element)
                    if(element.boundingBox!!.right > maxRight){
                        maxRight = element.boundingBox!!.right
                    }
                    if(element.boundingBox!!.left < minLeft){
                        minLeft = element.boundingBox!!.left
                    }
                }
            }
        }

        //sortowanie elementów i tworzenie linii
        recognizedElements.sortBy{ it.boundingBox!!.top }
        var iBefore = recognizedElements[0]
        var first = true
        val line = ArrayList<Text.Element>()
        for (i in recognizedElements){
            if (first){
                first = false
                continue
            } else {
                val topDiff = i.boundingBox!!.top - iBefore.boundingBox!!.top
                val height = (i.boundingBox!!.height() + iBefore.boundingBox!!.height()) / 2
                if(topDiff < height*0.3) {
                    line.add(iBefore)
                } else {
                    line.add(iBefore)
                    line.sortBy { it.boundingBox?.left }

                    var nameText = ""
                    var prizeText = ""
                    for (element in line){
                        val right = maxRight - element.boundingBox!!.right
                        if(right < 50){
                            prizeText += element.text
                        } else {
                            nameText += element.text + " "
                        }
                        text += element.text + " "
                    }
                    name.add(nameText)

                    prizeText = prizeText.filter { it.isDigit() || it == '-' || it == ',' || it == '.' }
                    prizeText.forEachIndexed {index, sign ->
                        if(sign == ',' || sign == '.'){
                            prizeText = prizeText.removeRange(index+3, prizeText.count())
                        }
                    }
                    prize.add(prizeText)

                    text += "\n"
                    line.clear()
                }
                iBefore = i
            }
        }
        //dodawanie ostatniej linii
        line.add(recognizedElements[recognizedElements.count()-1])
        line.sortBy { it.boundingBox?.left }
        var nameText = ""
        var prizeText = ""
        for (element in line){
            text += element.text + " "
            val right = maxRight - element.boundingBox!!.right
            if(right < 50){
                prizeText += element.text
            } else {
                nameText += element.text + " "
            }
        }
        name.add(nameText)

        prizeText = prizeText.filter { it.isDigit() || it == '-' || it == ',' || it == '.' }
        prizeText.forEachIndexed {index, sign ->
            if(sign == ',' || sign == '.'){
                prizeText = prizeText.removeRange(index+3, prizeText.count())
            }
        }
        prize.add(prizeText)



        //Odliczanie rabatu
        val rabatIndex = ArrayList<Int>()
        name.forEachIndexed{index, s ->
            val firstWord = s.removeRange(5, s.count())
            if(firstWord == "RABAT" || firstWord == "OPUST" || firstWord == "Rabat" || firstWord == "Opust" ){
                rabatIndex.add(index)
            }
        }
        if(rabatIndex.isNotEmpty()){
            val nameBuff = ArrayList<String>()
            val prizeBuff = ArrayList<String>()
            var j = rabatIndex.count() -1
            var rabatPrize = 0.0
            for (index in name.count() - 1 downTo 0){
                //newPrize = prize[index]
                if(index == rabatIndex[j]){
                    rabatPrize = prize[index].replace(",", ".").toDouble()
                    if(j != 0){
                        j--
                    }
                } else{
                    var newPrize = prize[index].replace(",", ".").toDouble() + rabatPrize
                    newPrize = round(newPrize*100) /100
                    nameBuff.add(0, name[index])
                    prizeBuff.add(0, newPrize.toString().replace(".", ","))
                    rabatPrize = 0.0
                }
            }
            name.clear()
            prize.clear()
            name.addAll(nameBuff)
            prize.addAll(prizeBuff)
        }
    }
    return text
}