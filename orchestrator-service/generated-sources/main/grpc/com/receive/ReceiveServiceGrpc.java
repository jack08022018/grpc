package com.receive;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: ReceiverService.proto")
public final class ReceiveServiceGrpc {

  private ReceiveServiceGrpc() {}

  public static final String SERVICE_NAME = "ReceiveService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.receive.ReceiveRequest,
      com.receive.ReceiveResponse> getCreditMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "credit",
      requestType = com.receive.ReceiveRequest.class,
      responseType = com.receive.ReceiveResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.receive.ReceiveRequest,
      com.receive.ReceiveResponse> getCreditMethod() {
    io.grpc.MethodDescriptor<com.receive.ReceiveRequest, com.receive.ReceiveResponse> getCreditMethod;
    if ((getCreditMethod = ReceiveServiceGrpc.getCreditMethod) == null) {
      synchronized (ReceiveServiceGrpc.class) {
        if ((getCreditMethod = ReceiveServiceGrpc.getCreditMethod) == null) {
          ReceiveServiceGrpc.getCreditMethod = getCreditMethod =
              io.grpc.MethodDescriptor.<com.receive.ReceiveRequest, com.receive.ReceiveResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "credit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.receive.ReceiveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.receive.ReceiveResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ReceiveServiceMethodDescriptorSupplier("credit"))
              .build();
        }
      }
    }
    return getCreditMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ReceiveServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReceiveServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReceiveServiceStub>() {
        @java.lang.Override
        public ReceiveServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReceiveServiceStub(channel, callOptions);
        }
      };
    return ReceiveServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ReceiveServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReceiveServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReceiveServiceBlockingStub>() {
        @java.lang.Override
        public ReceiveServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReceiveServiceBlockingStub(channel, callOptions);
        }
      };
    return ReceiveServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ReceiveServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReceiveServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReceiveServiceFutureStub>() {
        @java.lang.Override
        public ReceiveServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReceiveServiceFutureStub(channel, callOptions);
        }
      };
    return ReceiveServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ReceiveServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void credit(com.receive.ReceiveRequest request,
        io.grpc.stub.StreamObserver<com.receive.ReceiveResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreditMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreditMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.receive.ReceiveRequest,
                com.receive.ReceiveResponse>(
                  this, METHODID_CREDIT)))
          .build();
    }
  }

  /**
   */
  public static final class ReceiveServiceStub extends io.grpc.stub.AbstractAsyncStub<ReceiveServiceStub> {
    private ReceiveServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReceiveServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReceiveServiceStub(channel, callOptions);
    }

    /**
     */
    public void credit(com.receive.ReceiveRequest request,
        io.grpc.stub.StreamObserver<com.receive.ReceiveResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreditMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ReceiveServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ReceiveServiceBlockingStub> {
    private ReceiveServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReceiveServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReceiveServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.receive.ReceiveResponse credit(com.receive.ReceiveRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreditMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ReceiveServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ReceiveServiceFutureStub> {
    private ReceiveServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReceiveServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReceiveServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.receive.ReceiveResponse> credit(
        com.receive.ReceiveRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreditMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREDIT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ReceiveServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ReceiveServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREDIT:
          serviceImpl.credit((com.receive.ReceiveRequest) request,
              (io.grpc.stub.StreamObserver<com.receive.ReceiveResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ReceiveServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ReceiveServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.receive.ReceiverService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ReceiveService");
    }
  }

  private static final class ReceiveServiceFileDescriptorSupplier
      extends ReceiveServiceBaseDescriptorSupplier {
    ReceiveServiceFileDescriptorSupplier() {}
  }

  private static final class ReceiveServiceMethodDescriptorSupplier
      extends ReceiveServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ReceiveServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ReceiveServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ReceiveServiceFileDescriptorSupplier())
              .addMethod(getCreditMethod())
              .build();
        }
      }
    }
    return result;
  }
}
