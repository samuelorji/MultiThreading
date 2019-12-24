package dining_philosopher

import scala.util.Random

case class Philospher(id : Int, left : Chopstick, right : Chopstick ) extends Runnable {

  @volatile private var isFull = false
  private var numEaten = 0
  private val random = Random
  import Chopstick._

  def eat = {
    numEaten = numEaten + 1
    println(s"$this is eating .... ")
    Thread.sleep(random.nextInt(1000))
  }
  def think = {
    println(s"$this is thinking .... ")
    Thread.sleep(random.nextInt(1000))
  }


  def canPickUpLeft : Boolean = {
    left.pickChopstick(this,LEFT)
  }

  def canPickUpRight : Boolean = {
    right.pickChopstick(this,RIGHT)
  }

  def dropRight = right.dropChopstick(this,RIGHT)
  def dropLeft = left.dropChopstick(this,LEFT)


  def setFull = isFull = true


  override def run(): Unit = {
    while(!isFull){
      think
      if(canPickUpLeft){
        if(canPickUpRight){
          eat
          dropRight
        }
        dropLeft
      }
    }
  }

  def getNumEaten = numEaten

  override def toString: String = {
    s"Philosopher[$id]"
  }
}
