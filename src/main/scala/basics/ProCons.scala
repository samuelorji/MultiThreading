package basics

class ProCons() {
  def apply(unit: Unit) = ()

  private def printAndSleep(msg: String) = {
    println(msg)
   // Thread.sleep(time)
  }

  def produce() = {

    this.synchronized {
      for (i <- 1 to 10) {
        printAndSleep("I am Producing " + i)
      }
     // println("beginning production")
      // release the lock for someone to consume --> in this case [consume method]
      wait() // can only be called by the object that has the lock (monitor) ....

      println("Consumer hsa finished consuming")
    }
  }

  def consume() = {
    Thread.sleep(1000) //force the producer to run first
    this.synchronized{
      for (i <- 1 to 10) {
        printAndSleep("I am Consuming " + i)
      }
    //  println("beginning consumption")
      notify()
    }
  }
}

object test extends App {
  val proCons = new ProCons()

  val t1 = new Thread(() => proCons.produce())
  val t2 = new Thread(() => proCons.consume())
  t1.start()
  t2.start()

  t1.join()
  t2.join()

  //Thread.sleep(3000)
}
