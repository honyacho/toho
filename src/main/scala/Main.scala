package com.github.honyacho.toho

import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui._

import java.util.Date
import scala.collection.JavaConversions._

object Main {
  def main(args: Array[String]): Unit = {

    val target = "15:30"
    val targetDay = "12/11"
    val driver = new FirefoxDriver()

    driver.get("http://hlo.tohotheater.jp/net/schedule/076/TNPI2000J01.do")

    // starts on 9/30 0:00
    // while (System.currentTimeMillis() <= 1449241200000L) {
    //   println("まだ")
    //   println((new Date()).toString)
    //   Thread.sleep(100)
    // }

    driver.findElements(By.cssSelector(".schedule_list_1-date")).filter(_.getText.contains(targetDay)).foreach(_.click())

    val wait = new WebDriverWait(driver, 15);
    wait.until(new ExpectedCondition[Boolean]() {
      override def apply(d: WebDriver): Boolean = {
        d.findElements(By.xpath("//span[@class=\"schedule_table_3-date\"]/em[text()=\"" + target + "\"]"))
          .find(e => {
            try {
              val res = e.getText.contains(target)
              println(res)
              res
            } catch { case _: Throwable => false }
          })
          .find(e => {
            try {
              val elem = e.findElement(By.xpath("..")).findElement(By.xpath("..")).findElements(By.cssSelector(".schedule_table_btn_buy"))
              println("after: " + elem.nonEmpty)
              elem.foreach(_.click())
              elem.nonEmpty
            } catch { case _: Throwable => false }
          }).nonEmpty
      }
    })

    driver.findElement(By.cssSelector("#B-17")).click()
    driver.findElement(By.cssSelector("#bo-navi2>a")).click()

    println("Press ctrl + d to Exit.")
    val reader = new java.io.InputStreamReader(System.in);
    while (reader.read() != 4) {
    }

    driver.quit()
  }
}
