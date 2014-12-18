package io.vertx.ext.mail;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

/**
 * @author <a href="http://oss.lehmann.cx/">Alexander Lehmann</a>
 *
 */
public class CommandResultFuture implements Future<String> {

  private Handler<AsyncResult<String>> handler;
  private String result;
  private Throwable cause;

  public CommandResultFuture(Handler<String> handler) {
    this.setHandler(event -> {
      if (event.succeeded()) {
        handler.handle(event.result());
      } else {
        // FIXME: proper logging or propagate the exception
        event.cause().printStackTrace();
      }
    });
  }

  @Override
  public String result() {
    return result;
  }

  @Override
  public Throwable cause() {
    return cause;
  }

  @Override
  public boolean succeeded() {
    return result != null;
  }

  @Override
  public boolean failed() {
    return cause != null;
  }

  @Override
  public boolean isComplete() {
    return result != null || cause != null;
  }

  @Override
  public void setHandler(Handler<AsyncResult<String>> handler) {
    this.handler = handler;
  }

  @Override
  public void complete(String string) {
    result = string;
    handler.handle(this);
  }

  @Override
  public void fail(Throwable throwable) {
    cause = throwable;
    handler.handle(this);
  }

  @Override
  public void complete() {
    // TODO Auto-generated method stub

  }

  @Override
  public void fail(String failureMessage) {
    // TODO Auto-generated method stub

  }

}