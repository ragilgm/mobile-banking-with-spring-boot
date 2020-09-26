package com.finalproject.ragil.finalproject.rabbitmq.back;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.stereotype.Service;



@Service
public class BackConsumer {
    String pesan;
    String EXCHANGE_NAME = "mybanking_send";
    public String getPesan(){
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void recieveFromFront(){
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            System.out.println(" [*] Waiting for messages From Aplication. To exit press CTRL+C");
            GetResponse response = channel.basicGet(queueName, true);
            while (response == null){
                response = channel.basicGet(queueName, true);
            };

            String message = new String(
                    response.getBody(), "UTF-8");
            System.out.println("[Receiver] \t: Received : '" + message + "'");
            setPesan(message);

            channel.close();
            connection.close();

        }catch (Exception e){e.printStackTrace();}
    }






}
