package basics

import java.util.concurrent.{CountDownLatch, Executors}

import scala.util.Random

object Downloader {

  def downloadFirst = {
    println(s"Downloading first ..... ")
    Thread.sleep(3000)
  }

  def downloadSecond = {
    println("Downloading second .... ")
    Thread.sleep(3000)
  }

  def downloadThird = {
    println("Downloading third ... ")
    Thread.sleep(3000)
  }

  def downloadFourth = {
    println("Downloading Fourth ")
    Thread.sleep(3000)
  }

  def pickDownload(id : Int) = {
    id match {
      case 1 => downloadFirst
      case 2 => downloadSecond
      case 3 => downloadThird
      case 4 => downloadFourth
      case _ =>
    }
  }


}
trait MachineT {
  val latch : CountDownLatch
  def execute : Unit
}


class ImageProcessor(val latch : CountDownLatch, random : Random) extends MachineT {
  override def execute: Unit = {
    println("Downloading Image from ...... my ImageWebsite")
    Thread.sleep((random.nextInt(3) * 1000 ))
    latch.countDown()
  }
}

class AudioProcessor(val latch : CountDownLatch, random : Random) extends MachineT {
  override def execute: Unit = {
    val timeToSleep = random.nextInt(3) * 1000
    println(s"Downloading Audio from ...... my AudioWebsite. Sleeping for $timeToSleep seconds")

    Thread.sleep(timeToSleep)
    latch.countDown()
  }
}
class SignalProcessor(val latch : CountDownLatch, random : Random) extends MachineT {
  override def execute: Unit = {
    latch.await()
    println("Image and Audio should have downloaded ....I can now do my work ")
  }
}

class Processor(id : Int, val latch : CountDownLatch ) extends Runnable {
 override def run = {
    if (id == 4) {
      latch.await() // simulate waiting for all other latches to finish before moving
      println(s"Processor $id")
      Downloader.pickDownload(id)
    } else {
      println(s"Processor $id")
      Downloader.pickDownload(id)
      latch.countDown()
    }
  }
}
object CountDownLatchTest extends App {

  val executorServiceFixed  = Executors.newFixedThreadPool(3)
  val latch = new CountDownLatch(2)

  val random = new Random()
  executorServiceFixed.execute(() => new ImageProcessor(latch,random).execute )
  executorServiceFixed.execute(() => new AudioProcessor(latch,random).execute )
  executorServiceFixed.execute(() => new SignalProcessor(latch,random).execute )


 // executorServiceFixed.execute()


  executorServiceFixed.shutdown()



}
