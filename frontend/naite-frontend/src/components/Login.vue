<template>
  <div id="login">
    <div class="login-head">LOGIN</div>
    <form action="#" class='login-form'>
        <label for="id">ID</label><br>
        <input type="text" id='id' placeholder="아이디를 입력해주세요"><br>
        <label for="password">PASSWORD</label><br>
        <input type="password" id='password' placeholder="비밀번호를 입력해주세요"><br>
    </form>
    <div id='login-checkbox'>
        <label for="id-save">아이디 저장</label>
        <input type="checkbox" id='id-save'>
        <label for="auto-login" style='margin-left:3%;'>자동 로그인</label>
        <input type="checkbox" id='auto-login'>
    </div>
    <button class="login-btn">로그인</button>
    

    <div class="login-bottom">
        아이디 및 비밀번호 찾기 링크 <br>
        snslogin <br>
        signup btn <br>
        <button class="signup-btn" @click='look_signup'>회원가입</button>

    </div>



  </div>
</template>

<script>
export default {
    name:'Login',
    components:{
                
    },
    data: function() {
        return {
        }
    },
    methods:{
       look_signup: function(){
           const loginDiv = document.getElementById('login')
           loginDiv.style.left = '-50%'
           this.$emit('changeSignup')
       }
    },
    computed: {

    },
    props:{
        toLogin:Boolean,
    },
    
    watch:{
        toLogin: function(){
            if(this.toLogin){
                const loginDiv = document.getElementById('login')
                loginDiv.style.left = '10%'
            }

        }
    },
    created:{
        
    }
}
</script>



<style>
::placeholder {
    color: rgba(0,0,0,0.4)
}

#login {
    position: absolute;
    top: 50%;
    left: 10%;
    transform: translate(-50%,-50%);
    width: 20%;
    height:100%;
    background:rgb(255,255,255,1);
    transition: 0.3s;
}

#login::before{
    position:absolute;
    content:'';
    background-color:rgb(255,255,255,1);
    width: 100%;
    height:100%;
    left:12%;
    transform: skewX(-5deg);
    z-index: -1;
    
    
}

#login:hover {
    background:rgb(255,255,255,1);
}


.login-head {
    margin: 20% 0;
    font-size: 1.5rem;
}

.login-form{
    /* background-color: red; */
    text-align: left;
    margin-left: 5%;
    margin-right: 5%;
    font-size: 1.5rem;
}

.login-form > input {
    margin-top: 2%;
    height:35px;
    width: 100%;
    border-style: dotted;
    border-top: none;
    border-left: none;
    border-right: none;
    outline: none;
    background-color: transparent;
    padding:0 5px;
    font-size: 1.3rem;

}
.login-form > input[id='id']{
    margin-bottom: 8%;
}


#login-checkbox {
    text-align: left;
    margin: 5%;
    
}

.login-btn {
    position: absolute;
    top: 39.5%;
    right:2%;
    transform: translateY(-50%);
    background-color: rgb(119, 119, 240);
    border-color:rgb(119, 119, 240);
    color: white;
    border-radius: 5px;
    width: 25%;
    height: 3%;

}

.login-bottom {
    position:relative;
    background-color: rgb(216, 150, 150);
    margin-left: 5%;
    height:50%;
}


</style>
