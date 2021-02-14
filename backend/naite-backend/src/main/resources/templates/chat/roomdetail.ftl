<!doctype html>
<html lang="en">
<head>
    <title>Websocket ChatRoom</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div>
        <h2>{{room.name}}</h2>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">내용</label>
        </div>
        <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="sendMessage">보내기</button>
        </div>
    </div>
    <ul class="list-group">
        <li class="list-group-item" v-for="message in messages">
            {{message.userNick}} - {{message.message}} :: {{message.time}} / {{message.userPic}}</a>
        </li>
    </ul>
    <div></div>
</div>
<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script>
    //alert(document.title);
    // websocket & stomp initialize
    var sock = new SockJS("/ws-stomp");
    var ws = Stomp.over(sock);
    var reconnect = 0;
    // vue.js
    var vm = new Vue({
        el: '#app',
        data: {
            roomNo: 14,
            room: {},
            message: '',
            messages: []
        },
        created() {
            this.roomNo = localStorage.getItem('roomNo');
            this.findRoom();
        },
        methods: {
            findRoom: function() {
                axios.get('/chat/room/'+this.roomNo, {
                    headers: {
                        'auth-token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoLXRva2VuIiwiZXhwIjoxNjEzMjMzNTk2LCJ1c2VyIjp7InVzZXJObyI6MSwidXNlcklkIjoibWluZyIsInVzZXJOYW1lIjoi6rmA66-86rK9IiwidXNlckVtYWlsIjoia2ltbWswOTI0QGdtYWlsLmNvbSIsInVzZXJQdyI6IiQyYSQxMCR1QTljRC5jNEdvaHQyOWRHSFZwTWplVkllQ3F4N3cvSW1jeDBJbzhhR3Q5Lm9Ia0dPYVVMcSIsInVzZXJTYWx0IjoiJDJhJDEwJHVBOWNELmM0R29odDI5ZEdIVnBNamUiLCJ1c2VyQmFzaWNBZGRyZXNzIjoibWtiYXNpYyIsInVzZXJEZXRhaWxBZGRyZXNzIjoibWtkZXRhaWwiLCJ1c2VyTmljayI6Im1pbmciLCJ1c2VyU2NvcmUiOjQyLCJ1c2VyUmVwb3J0Q250IjowLCJ1c2VyUGljIjoibWtwaWMiLCJ1c2VyQWN0aXZlIjoxfSwiZ3JlZXRpbmciOiJtaW5n64uYIO2ZmOyYge2VqeuLiOuLpC4ifQ.NLToERJgn7tpH_iDrgreIgn-WE4eRhX_ycxdDXAw4t4'
                    }
                }).then(response => {
                    this.messages = response.data.data;
                });
            },
            sendMessage: function() {
                axios.post('/chat/message', {roomNo: this.roomNo, message: this.message}, {
                    headers: {
                        'auth-token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoLXRva2VuIiwiZXhwIjoxNjEzMjMzNTk2LCJ1c2VyIjp7InVzZXJObyI6MSwidXNlcklkIjoibWluZyIsInVzZXJOYW1lIjoi6rmA66-86rK9IiwidXNlckVtYWlsIjoia2ltbWswOTI0QGdtYWlsLmNvbSIsInVzZXJQdyI6IiQyYSQxMCR1QTljRC5jNEdvaHQyOWRHSFZwTWplVkllQ3F4N3cvSW1jeDBJbzhhR3Q5Lm9Ia0dPYVVMcSIsInVzZXJTYWx0IjoiJDJhJDEwJHVBOWNELmM0R29odDI5ZEdIVnBNamUiLCJ1c2VyQmFzaWNBZGRyZXNzIjoibWtiYXNpYyIsInVzZXJEZXRhaWxBZGRyZXNzIjoibWtkZXRhaWwiLCJ1c2VyTmljayI6Im1pbmciLCJ1c2VyU2NvcmUiOjQyLCJ1c2VyUmVwb3J0Q250IjowLCJ1c2VyUGljIjoibWtwaWMiLCJ1c2VyQWN0aXZlIjoxfSwiZ3JlZXRpbmciOiJtaW5n64uYIO2ZmOyYge2VqeuLiOuLpC4ifQ.NLToERJgn7tpH_iDrgreIgn-WE4eRhX_ycxdDXAw4t4'
                    }
                })
                     .then((response) => {
                            console.log(response.data);

                            ws.send("/pub/chat/message", {}, JSON.stringify({
                                roomNo: response.data.roomNo,
                                messageNo: response.data.messageNo,
                                message: response.data.message,
                                time: response.data.time,
                                userNick: response.data.userNick,
                                userPic: response.data.userPic,
                                userOwn: true
                            }))
                        }
                    )
                    .catch( response => { alert("메세지 전송에 실패하였습니다."); } );
                this.message = '';
            },
            recvMessage: function(recv) {
                console.log("RECV " + recv)
                // this.messages.unshift({"messageNo":recv.messageNo,"message":recv.message, "time":recv.time, "userNick": recv.userNick, "userPic":recv.userPic ,"userOwn": recv.userOwn})
                this.messages.push({"messageNo":recv.messageNo,"message":recv.message, "time":recv.time, "userNick": recv.userNick, "userPic":recv.userPic ,"userOwn": recv.userOwn})

            }
        }
    });

    function connect() {
        // pub/sub event
        ws.connect({}, function(frame) {
            ws.subscribe("/sub/chat/room/"+vm.$data.roomNo, function(message) {
                var recv = JSON.parse(message.body);
                console.log(message)
                vm.recvMessage(recv);
            });
        }, function(error) {
            if(reconnect++ <= 5) {
                setTimeout(function() {
                    console.log("connection reconnect");
                    sock = new SockJS("/ws-stomp");
                    ws = Stomp.over(sock);
                    connect();
                },10*1000);
            }
        });
    }
    connect();
</script>
</body>
</html>