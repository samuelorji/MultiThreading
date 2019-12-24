package basics


import java.util.concurrent.{ BlockingQueue,PriorityBlockingQueue }

import scala.collection.mutable

object PriorityQueueTest extends App {

  val queue = new PriorityBlockingQueue[Person]

  new Thread(new PersonProducer(queue)).start()
  new Thread(new PersonConsumer(queue)).start()

}

case class Person(name : String, age : Int) extends Comparable[Person]{
  override def compareTo(o: Person): Int = {
    this.age.compareTo(o.age)
  }

  override def toString: String = {
    s"$name -> " + age
  }
}

class PersonProducer(queue : BlockingQueue[Person]) extends Runnable {
  val popo = Person("popo", 27)
  val chingy = Person("chingy", 26)
  val uche = Person("uche", 25)
  val samuel = Person("samuel", 22)
  override def run(): Unit = {
    queue.put(uche)
    Thread.sleep(1000)
    queue.put(popo)
    Thread.sleep(1000)
    queue.put(samuel)
    Thread.sleep(1000)
    queue.put(chingy)
    println("done inserting into the queue")

  }
}

class PersonConsumer(queue : BlockingQueue[Person]) extends Runnable {
  override def run(): Unit = {
    Thread.sleep(5000)
    while(!queue.isEmpty){
      println(queue.take())
    }
  }
}
