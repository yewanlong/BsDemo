package com.beisheng.mybslibary.utils.cache;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BSMyThreadPool {
  private ExecutorService executorService;

  private static int poolSize = 8;

  private static BSMyThreadPool pool = new BSMyThreadPool();

  private BSMyThreadPool(){
    executorService = Executors.newFixedThreadPool(poolSize);

  }

  public static BSMyThreadPool getInstance(){
    return pool;
  }

  public void execute(Runnable cmd){
    executorService.execute(cmd);
  }

  public void shutdown(){
    executorService.shutdown();
  }

  public boolean isTerminated(){
    return isTerminated(1);
  }

  public boolean isTerminated(int intval){
    while(!executorService.isTerminated()){
      try {
        Thread.sleep(1000 * intval);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return true;
  }
  
  public boolean isShutdown(){
    return executorService.isShutdown();
  }
  
}

