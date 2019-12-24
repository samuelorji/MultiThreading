package dining_philosopher

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

class Chopstick(id : Int) {
  // a chopstick can either be picked up or put down, similarly, It can either be a left or right chopstick
  import Chopstick._
  private[this] val lock = new ReentrantLock()


  def pickChopstick(phil : Philospher,state : CHOPSTICK) : Boolean = {
    if(lock.tryLock(50, TimeUnit.MILLISECONDS)){
      println(s"$phil picked up $state $this")
      true
    }else{
      false
    }
  }

  def dropChopstick(phil : Philospher , state : CHOPSTICK) : Unit = {
    lock.unlock()
    println(s"$phil dropped $state $this")
  }

  override def toString: String = {
    s"Chopstick[$id]"
  }

}

object Chopstick extends Enumeration {
  type CHOPSTICK = Value
  val LEFT,RIGHT = Value
}
