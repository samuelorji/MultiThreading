package basics

import java.util.concurrent.{ExecutorService, Executors, Semaphore}


object DownloaderSem {

  val semaphore = new Semaphore(5)
 private def downloadImpl = {
    println("Downloading ....from the web  ")
    Thread.sleep(3000)
  }


  def download  = {

   // semaphore.acquire()
    try {
      downloadImpl
    }catch {
      case e  => println(s"Encountered an exception ", e)
    }finally {
      //semaphore.release()
    }
  }
}


object DownloaderTestWithSemaphores extends App {

  val executorServiceCached = Executors.newCachedThreadPool()

  val executorServiceFixed = Executors.newFixedThreadPool(5)


  for {i <- 1 to 10}{
    executorServiceCached.execute(() => DownloaderSem.download)
  }

//  for {
//    i <- 1 to 100
//  }{
//    executorService.execute(() => Downloader.download)
//  }


  executorServiceFixed.shutdown()


  executorServiceCached.shutdown()
}
