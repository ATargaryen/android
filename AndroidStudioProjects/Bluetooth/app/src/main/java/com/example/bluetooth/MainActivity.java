// THIS APP BASICALLY TO TURN ON & OFF THE BLUETOOTH  : DEVELOP BY IMPONIA
package com.example.bluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    int requestCodeForeEnable;

    BluetoothAdapter bluetoothAdapter;
    Intent enableBtIntent;
    Button bluetoothON , BluetoothOFF ;
    private int requestCode;
    private int resultCode;
    private Intent data;


    //Declare UI components
    Button listen,send, listDevices;
    ListView listView;
    TextView msg_box,status;
    EditText writeMsg;

    BluetoothDevice[] btArray;  // used to list paired devices

     //  used to checking STATUS
    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING=2;
    static final int STATE_CONNECTED=3;
    static final int STATE_CONNECTION_FAILED=4;
    static final int STATE_MESSAGE_RECEIVED=5;


    // used these  variable for communicationg with another bluetooth device
    private static final String APP_NAME = "BTChat";
    // uuid is generated online : unique for every device create your own
    private static final UUID MY_UUID=UUID.fromString("f6796d9a-b5d6-11ea-b3de-0242ac130004");


    SendReceive sendReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bluetoothON = findViewById(R.id.buttonon);          // getting the ON button from UI
        BluetoothOFF = findViewById(R.id.buttonoff);          // getting the OFF button from UI
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();         // Main thing for all Bluetooth operation
        enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);       // Intent used to enable bluetooth

        requestCodeForeEnable =1;    // used to enabling bluetooth , its value is >0 FOR ENABLING


        BluetoothOn();        // CALL METHOD ON
        BluetoothOFF();          // CALL METHOD OFF
        findViewByIdes();        // get or link UI component with main activity

        implementListeners(); // listeners or Catch event and perform action
    }

    private void implementListeners() {
        listDevices.setOnClickListener(new View.OnClickListener() {

            // used to list all the paired devices
            @Override
            public void onClick(View view) {
                Set<BluetoothDevice> bt=bluetoothAdapter.getBondedDevices();
                String[] strings=new String[bt.size()];
                btArray=new BluetoothDevice[bt.size()];
                int index=0;

                if( bt.size()>0)
                {
                    for(BluetoothDevice device : bt)
                    {
                        btArray[index]= device;
                        strings[index]=device.getName();
                        index++;
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,strings);
                    listView.setAdapter(arrayAdapter);
                }
            }
        });

        listen.setOnClickListener(new View.OnClickListener() {

            // when listen button press then Server started
            @Override
            public void onClick(View view) {
                ServerClass serverClass=new ServerClass();
                serverClass.start();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // when we click on item in paired device to  client start
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClientClass clientClass=new ClientClass(btArray[i]);
                clientClass.start();   // read client

                status.setText("Connecting");
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            // when send to pressed
            @Override
            public void onClick(View view) {
                // used to send the message to server
                String string= String.valueOf(writeMsg.getText());  // get the message from textview UI
                sendReceive.write(string.getBytes());
            }
        });
    }
    Handler handler=new Handler(new Handler.Callback() {
        // HANDLE : USED TO LISTEN FORM ONE THREAD AND SHOW OUTPUT ON UI ... you direct from background thread to UI can't pass messages u need Handler for it
        @Override
        public boolean handleMessage(Message msg) {

            // used to show the status of processing

            switch (msg.what)
            {
                case STATE_LISTENING:
                    status.setText("Listening");
                    break;
                case STATE_CONNECTING:
                    status.setText("Connecting");
                    break;
                case STATE_CONNECTED:
                    status.setText("Connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    status.setText("Connection Failed");
                    break;
                case STATE_MESSAGE_RECEIVED:
                    // when get the message send it to UI
                    byte[] readBuff= (byte[]) msg.obj;
                    String tempMsg=new String(readBuff,0,msg.arg1);
                    msg_box.setText(tempMsg);
                    break;
            }
            return true;
        }
    });


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // used to show the result Bluetooth turn ON or NOT
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeForeEnable) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth now ON", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Bluetooth on error", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void BluetoothOn() {
       // used to on the bluetooth
        bluetoothON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {         // set event listner to ON(UI) button
                if (bluetoothAdapter == null) {
                    // Device doesn't support Bluetooth
                    // used to check Device support Bluetooth or not
                    Toast.makeText(getApplicationContext(),"Bluetooth NOT SUPPORTED DEVICE" , Toast.LENGTH_LONG).show();
                } else  {

                    // if device support then enable it
                    if (!bluetoothAdapter.isEnabled()) {          //  check enable or not :THIS METHOD bluetoothAdapter.isEnabled() RETURN FALSE SO VIA ! NOT OPERATOR WE MAKE IT TRUE
                           // !bluetoothAdapter.isEnabled() RETURN TRUE it means enable the bluetooth NOW
                        startActivityForResult(enableBtIntent,requestCodeForeEnable);  // ENABLEING.... method take two arg 1 - intent 2 - +variable
                    }
                }

            }
        });

    }
    private void BluetoothOFF() {
        // used to turn off the Bluetooth
        BluetoothOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Bluetooth Turn OFF", Toast.LENGTH_LONG).show();
                         bluetoothAdapter.disable();   // Disable
                }
            }
        });

    }


    private void findViewByIdes() {

        // used to get UI components
        listen=(Button) findViewById(R.id.listen);
        send=(Button) findViewById(R.id.send);
        listView=(ListView) findViewById(R.id.listvieww);
        msg_box =(TextView) findViewById(R.id.msg);
        status=(TextView) findViewById(R.id.status);
        writeMsg=(EditText) findViewById(R.id.writemsg);
        listDevices=(Button) findViewById(R.id.listDevices);
    }

    private class ServerClass extends Thread
    {   // used communication : SERVER
        private BluetoothServerSocket serverSocket;   //

        public ServerClass(){
            try {
                // create a socket for listening request
                serverSocket=bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME,MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run()
        {
            BluetoothSocket socket=null;

            while (socket==null)
            {
                try {
                    // used for accepting request
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTING;
                    handler.sendMessage(message);  // send status connecting to handler

                    socket=serverSocket.accept();    // accepting the request
                } catch (IOException e) {
                    // if connection not created the
                    e.printStackTrace();
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTION_FAILED;  // send status FAILED TO handler
                    handler.sendMessage(message);
                }

                if(socket!=null)
                {
                     // finally when connection established
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTED;
                    handler.sendMessage(message); // send status connected TO handler

                    sendReceive=new SendReceive(socket);
                    sendReceive.start();   // start passing messages

                    break;
                }
            }
        }
    }

    private class ClientClass extends Thread
    {   // client for communication
        private BluetoothDevice device;
        private BluetoothSocket socket;   // client socket

        public ClientClass (BluetoothDevice device1)   // constructor arg: device which you want connect
        {
            device=device1;

            // for the device we get from argument we open a socket to connection

            try {
                socket=device.createRfcommSocketToServiceRecord(MY_UUID);   // create socket
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run()
        {
            try {
                // AFTER socket open we connect it to server
                socket.connect();
                Message message=Message.obtain();
                message.what=STATE_CONNECTED;   // send status connected to handler
                handler.sendMessage(message);

                sendReceive=new SendReceive(socket);
                sendReceive.start();

            } catch (IOException e) {
                e.printStackTrace();
                Message message=Message.obtain();
                message.what=STATE_CONNECTION_FAILED; // send status failed to handler
                handler.sendMessage(message);
            }
        }
    }

    private class SendReceive extends Thread
    {   // used for send recieve messages
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;   //  used to getting the message
        private final OutputStream outputStream;   //  used to sending the message

        public SendReceive (BluetoothSocket socket)   // Constructor used to intailize variable
        {
            bluetoothSocket=socket;
            InputStream tempIn=null;
            OutputStream tempOut=null;

            try {
                tempIn=bluetoothSocket.getInputStream();
                tempOut=bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream=tempIn;
            outputStream=tempOut;
        }

        public void run()
        {
            byte[] buffer=new byte[1024];  // message
            int bytes;   // message length

            while (true)
            {
                try {
                    bytes=inputStream.read(buffer);  // number of bytes
                    // (status , no of bytes , arg2 not used to -1 , message ).send to target
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}