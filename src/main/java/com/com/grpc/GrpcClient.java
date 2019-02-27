package com.com.grpc;

import com.netty.lecture.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;

public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8899).usePlaintext().build();

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(channel);

        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(channel);
//
//        MyResponse response = blockingStub.getRealNameByUserName(MyRequest.newBuilder().setUsername("张三").build());
//
//        System.out.println(response.getRealname());
//
//        System.out.println("---------------------------------------------");
//
//        // 流式对象
//        Iterator<StudentResponse> ite = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());
//        while(ite.hasNext()){
//            StudentResponse next = ite.next();
//
//            System.out.println(next.getName() + "," + next.getAge() + "," +next.getCity());
//        }
//
//        System.out.println("---------------------------------------------");
//
//        StreamObserver<StudentResponseList> studentResponseListStreamObserver =
//                new StreamObserver<StudentResponseList>() {
//
//                    @Override
//                    public void onNext(StudentResponseList value) {
//                        value.getStudentResponseList().forEach(studentResponse -> {
//                            System.out.println(studentResponse.getName());
//                            System.out.println(studentResponse.getAge());
//                            System.out.println(studentResponse.getCity());
//                            System.out.println("*********");
//                        });
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        System.out.println(t.getMessage());
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("completed");
//                    }
//                };
//
//        StreamObserver<StudentRequest> studentRequestStreamObserver =
//                stub.getStudentsWrapperByAges(studentResponseListStreamObserver);
//
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());
//
//        studentRequestStreamObserver.onCompleted();

        StreamObserver<StreamRequest> requestStreamObserver =
                stub.biTalk(new StreamObserver<StreamResponse>() {
                    @Override
                    public void onNext(StreamResponse value) {
                        System.out.println(value.getResponseInfo());
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(t.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }
                });

        for(int i=0 ; i < 10 ; ++i){
            requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
