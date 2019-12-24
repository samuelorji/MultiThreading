package dining_philosopher

import java.util.concurrent.Executors

object Dining_philosopher_test extends App {

  val chopsticks = (1 to 5).map(new Chopstick(_))

  val philosophers = Array.ofDim[Philospher](5)

  val executorService = Executors.newFixedThreadPool(5)

  for (i <- 0 to 4){
    philosophers(i) = Philospher(i+1 , chopsticks(i), chopsticks((i + 1) % 5))
    executorService.execute(philosophers(i))
  }

  Thread.sleep(20000)

  philosophers.map(_.setFull)
  executorService.shutdown()

  while(!executorService.isTerminated){
    Thread.sleep(1000) //wait for executor service to properly shutdown
  }

  philosophers.foreach{ phil =>
    println(s"$phil ate ${phil.getNumEaten} times")
  }

}
