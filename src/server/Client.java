package server;//import java.io.*;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//public class server.Client {
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        /**
//         * server.Client wishes to initiate a connection with a remote/local server
//         * We need:
//         * 1. address of the server
//         * 2. port
//         *
//         * There are 2 kinds of sockets:
//         * ServerSocket- listens and accepts connections
//         * Operational socket (client socket) - 2 way pipeline to read/write messages
//         */
//        Socket clientSocket = new Socket("127.0.0.1",8010);
//        System.out.println("server.Client: Socket created");
//
//        InputStream inputStream = clientSocket.getInputStream();
//        OutputStream outputStream = clientSocket.getOutputStream();
//        ObjectInputStream fromServer = new ObjectInputStream(inputStream);
//        ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
//
//        // create 2d array
//        int[][] source = {
//                {0,1,1},
//                {0,1,0},
//                {0,1,1},
//        };
//        // send "matrix" command
//        toServer.writeObject("matrix");
//        toServer.writeObject(source);
//
//        // send "getNeighbors" command and then send an index
//        toServer.writeObject("getNeighbors");
//        server.Index sourceIndex = new server.Index(1,1);
//        toServer.writeObject(sourceIndex);
//        List<server.Index> neighbors = new ArrayList<>((List<server.Index>)fromServer.readObject());
//        System.out.println("server.Client: neighbors are: "+ neighbors);
//
//        toServer.writeObject("getReachables");
//        toServer.writeObject(sourceIndex);
//        List<server.Index> reachableIndices = new ArrayList<>((List<server.Index>)fromServer.readObject());
//        System.out.println("server.Client: neighbors are: "+ reachableIndices);
//
//        toServer.writeObject("stop");
//        // orderly shutdown all streams
//        System.out.println("Closing streams");
//        fromServer.close();
//        toServer.close();
//        clientSocket.close();
//    }
//}

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket =new Socket("127.0.0.1",8010);
        System.out.println("client: Created Socket");

        ObjectOutputStream toServer=new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream fromServer=new ObjectInputStream(socket.getInputStream());

        // sending #1 matrix
        int[][] source = {
                {0, 1, 0},
                {1, 0, 1},
                {1, 0, 1},
        };


        //send "matrix" command then write 2d array to socket
        toServer.writeObject("matrix");
        toServer.writeObject(source);

        //send "neighbors" command then write an index to socket
        toServer.writeObject("getNeighbors");
        toServer.writeObject(new Index(1,1));

        // get neighboring indices as list
        List<Index> AdjacentIndices =
                new ArrayList<Index>((List<Index>) fromServer.readObject());
        System.out.println("from client - Neighboring Indices are: "+ AdjacentIndices);

        //send "reachables" command then write an index to socket
        toServer.writeObject("getReachables");
        toServer.writeObject(new Index(1,1));

        // get reachable indices as list
        List<Index> reachables =
                new ArrayList<Index>((List<Index>) fromServer.readObject());
        System.out.println("from client - Reachable Indices are:  "+ reachables);

        toServer.writeObject("stop");
        System.out.println("client: Close all streams");
        fromServer.close();
        toServer.close();
        socket.close();
        System.out.println("client: Closed operational socket");
    }
}

