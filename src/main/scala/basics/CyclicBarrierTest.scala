package basics

import java.util.concurrent.{CyclicBarrier, Executors}

import scala.util.Random


class Worker(id : Int , barrier : CyclicBarrier ) extends Runnable {
  private val random = Random
  override def run(): Unit = {
    doWork
    barrier.await() // wait after countdown is zero before starting the cyclic barriers runnable
    println("Some other task i can call even after calling await on the cyclic barrier ")
  }

  private def doWork = {
    println(s"Thread $id is doing some work ...... " )
    Thread.sleep(random.nextInt(3000))
  }
}

object CyclicBarrierTest extends App {

  val service  = Executors.newFixedThreadPool(5)
  val cyclicBarrier = new CyclicBarrier(
    5 , //number of threads to wait on before calling barrier action
    () => {
      println("From the cyclic barrier, all threads have finished their computation ..... I can now do my work ")
    }
  )

  for (i <- 1 to 5)
    service.execute(new Worker(i,cyclicBarrier))

  service.shutdown()
}

