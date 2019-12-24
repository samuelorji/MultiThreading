package basics

import java.util.concurrent.{Executors, Semaphore}

object  Incrementer {
  private var count = 0
  //val sem = new Semaphore(5)
  private var isNotCompleted = true

  def inc = {
    this.synchronized {
      if (getCount == 100) {
        isNotCompleted = false
      } else {
        println(s"I am processed by ${Thread.currentThread().getName}")
        count = count + 1
      }
    }
  }

  def isNotComplete = this.synchronized{
    isNotCompleted
  }

  def getCount = this.synchronized(
    count
  )
}


object IncrementerTest extends App {

  val service = Executors.newCachedThreadPool()
  while(Incrementer.isNotComplete){
    service.execute(() => Incrementer.inc)
 }

  println(Incrementer.getCount)

  service.shutdownNow()


}
