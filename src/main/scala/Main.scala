package com.github.honyacho.toho

import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui._

import java.util.Date
import scala.collection.JavaConversions._

object Main {
  def main(args: Array[String]) {

    val target = "17:20"
    val driver = new FirefoxDriver()

    driver.get("https://hlo.tohotheater.jp/net/schedule/032/TNPI2000J01.do")

    // starts on 9/30 0:00
    while (System.currentTimeMillis() <= 1443538800000L) {
      println("まだ")
      println((new Date()).toString)
      Thread.sleep(333)
    }

    driver.findElements(By.cssSelector(".schedule_list_1-date")).filter(_.getText.contains("10/4")).foreach(_.click())

    val wait = new WebDriverWait(driver, 15);
    wait.until(new ExpectedCondition[Boolean]() {
      override def apply(d: WebDriver): Boolean = {
        d.findElements(By.xpath("//span[@class=\"schedule_table_3-date\"]/em[text()=\"" + target + "\"]"))
          .find(e => {
            try {
              val res = e.getText.contains(target)
              println(res)
              res
            } catch { case _ => false }
          })
          .find(e => {
            try {
              val elem = e.findElement(By.xpath("..")).findElement(By.xpath("..")).findElements(By.cssSelector(".schedule_table_btn_buy"))
              println("after: " + elem.nonEmpty)
              elem.foreach(_.click())
              elem.nonEmpty
            } catch { case _ => false }
          }).nonEmpty
      }
    })

    driver.findElement(By.cssSelector("#B-13")).click()
    driver.findElement(By.cssSelector("#B-14")).click()
    driver.findElement(By.cssSelector("#B-15")).click()
    driver.findElement(By.cssSelector("#B-16")).click()
    driver.findElement(By.cssSelector("#B-17")).click()
    driver.findElement(By.cssSelector("#B-18")).click()
    driver.findElement(By.cssSelector("#bo-navi2>a")).click()

    println("Type \"close\" if you want to close browser.")
    while (readLine() != "close") {
      println("Type \"close\" if you want to close browser.")
    }

    driver.quit()
  }
}
