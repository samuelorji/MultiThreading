package basics

import java.util.concurrent.{ArrayBlockingQueue, BlockingQueue}


class Producer(queue : BlockingQueue[Int]) extends Runnable {
  override def run(): Unit = {
    var counter = 0
    while(true){
      counter = counter + 1
      println(s"Adding $counter to the queue")
      queue.add(counter)
      Thread.sleep(1000)
    }
  }
}

class Consumer(queue : BlockingQueue[Int]) extends Runnable {
  override def run(): Unit = {

    while(true){
      val gotten = queue.take()
      println(s"Got $gotten from the queue")
      Thread.sleep(100)
    }
  }
}



object BlockingQueueTest extends App {

//  val blockingQueue = new ArrayBlockingQueue[Int](1000)
  ////
  ////  new Thread(new Producer(blockingQueue)).start()
  ////  new Thread(new Consumer(blockingQueue)).start()


  //val some =




}
