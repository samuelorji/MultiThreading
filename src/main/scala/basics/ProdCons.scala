package basics

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success}

class ProdCons {

  private var listBuffer : ListBuffer[Int] = ListBuffer.empty[Int]
  private val MAX = 5
  private val MIN = 0
  private var value = 0

  def produce = {

    this.synchronized {
      while (true) {
        if (listBuffer.size == MAX) {//we should stop producing and wait for consumer
          println("Producer at maximum, waiting on Consumer")
          wait()
        }else{
          //we're not yet at maximum
          println(s"Adding $value to the list")
          listBuffer.append(value)
          value = value + 1
          notify()
        }
        Thread.sleep(500)
      }
    }
  }


  def consume = {
    this.synchronized{
      while(true){
        if(listBuffer.isEmpty){
          //wait on producer to produce
          println(s"Waiting on producer ... ")
          wait()
        }else{
          println(s"got ${listBuffer.head} from list ")
          listBuffer = listBuffer.tail
          notify() // this is not a terminal operatoi
        }

        Thread.sleep(500)
      }
    }
  }
}

object test2 extends App {
  val proCons = new ProdCons()
//
  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global
//  val t1 = new Thread(() => proCons.produce)
//  val t2 = new Thread(() => proCons.consume)
//  t1.start()
//  t2.start()

 val fut =  for {
    num <- Future(4)
    if(num > 5)
  } yield num

  fut.onComplete{
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
  Thread.sleep(1000)

  //Thread.sleep(120)

  //System.exit(0)

}
