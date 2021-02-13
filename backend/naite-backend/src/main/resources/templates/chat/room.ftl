<!doctype html>
<html lang="en">
<head>
    <title>Websocket Chat</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!--<meta http-equiv="Content-Type" content="text/css; charset=UTF-8" />-->

    <!-- CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div class="row">
        <div class="col-md-12">
            <h3>채팅방 리스트</h3>
        </div>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">방제목</label>
        </div>
        <input type="text" class="form-control" v-model="other_nick" v-on:keyup.enter="createRoom">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="createRoom">채팅방 개설</button>
        </div>
    </div>
    <ul class="list-group">
        <li class="list-group-item list-group-item-action" v-for="item in chatrooms" v-bind:key="item.roomNo" v-on:click="enterRoom(item.roomNo)">
            {{item.otherNick}}님과의 채팅방  {{item.lastMessage}}  {{item.lastMessageTime}} {{item.otherPic}}
        </li>
    </ul>
</div>
<!-- JavaScript -->
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.18/vue.min.js"></script>-->
<!--<script src="https://unpkg.com/axios/dist/axios.min.js"></script>-->
<script type="text/javascript" src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script type="text/javascript" src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script>
    var vm = new Vue({
        el: '#app',
        data: {
            other_nick : '',
            chatrooms: [
            ]
        },
        created() {
            this.findAllRoom();
        },
        methods: {
            findAllRoom: function() {
                axios.get('/chat/rooms', {
                    headers: {
                        'auth-token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoLXRva2VuIiwiZXhwIjoxNjEzMjA4MTQ1LCJ1c2VyIjp7InVzZXJObyI6MSwidXNlcklkIjoibWluZyIsInVzZXJOYW1lIjoi6rmA66-86rK9IiwidXNlckVtYWlsIjoia2ltbWswOTI0QGdtYWlsLmNvbSIsInVzZXJQdyI6IiQyYSQxMCR1QTljRC5jNEdvaHQyOWRHSFZwTWplVkllQ3F4N3cvSW1jeDBJbzhhR3Q5Lm9Ia0dPYVVMcSIsInVzZXJTYWx0IjoiJDJhJDEwJHVBOWNELmM0R29odDI5ZEdIVnBNamUiLCJ1c2VyQmFzaWNBZGRyZXNzIjoibWtiYXNpYyIsInVzZXJEZXRhaWxBZGRyZXNzIjoibWtkZXRhaWwiLCJ1c2VyTmljayI6Im1pbmciLCJ1c2VyU2NvcmUiOjQyLCJ1c2VyUmVwb3J0Q250IjowLCJ1c2VyUGljIjoibWtwaWMiLCJ1c2VyQWN0aXZlIjoxfSwiZ3JlZXRpbmciOiJtaW5n64uYIO2ZmOyYge2VqeuLiOuLpC4ifQ.mqdDtk8WkxgRykMix9Ej-_-USxxRQzLae1Y12YsYjAg'
                    }
                }).then(response => {
                    this.chatrooms = response.data;
                    console.log(response);
                });
            },
            createRoom: function() {
                if("" === this.other_nick) {
                    alert("상대방 닉네임을 입력해주세요.");
                    return;
                } else {
                    var params = new URLSearchParams();
                    params.append("otherNick",this.other_nick);

                    axios.post('/chat/room', params)
                        .then(
                            response => {
                                alert(response.data+"번 방 입니다.")
                                this.other_nick = '';
                                this.findAllRoom();
                            }
                        )
                        .catch( response => { alert("채팅방 개설에 실패하였습니다."); } );
                }
            },
            enterRoom: function(roomNo) {
                localStorage.setItem('roomNo',roomNo);
                location.href="/chat/room/enter/"+roomNo;
            }
        }
    });
</script>
</body>
</html>