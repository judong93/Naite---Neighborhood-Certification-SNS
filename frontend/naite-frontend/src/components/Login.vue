<template>
  <div id="login">
    <div class="loginBox">
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
            <span @click='look_signup'>Don't have an account? Signup</span>
        </div>
        
        <button class="login-btn">로그인</button>
        <button class="login-btn-sns">네이버로 로그인 하기</button>
        <span></span>
        
    </div>
    
    

    <div class="login-bottom">
        아이디 및 비밀번호 찾기 링크 <br>
        snslogin <br>
        

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
           loginDiv.style.left = '-100%'
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
                loginDiv.style.left = '0%'
            }

        }
    },
}
</script>



<style>
::placeholder {
    color: rgba(255,255,255,0.6)
}

@keyframes fadeout {
    from {
        opacity: 1;
    } 
    to {
        opacity: 0;
    }
}


#login {
    position: absolute;
    top: 0%;
    left: 0%;
    width: 100%;
    height:100%;
    background:rgb(0,0,0,0.7);
    transition: 0.3s;
    font-family: font1;
    color:white;
    
}

.loginBox {
    position:relative;
    width: 700px;
    height: 1000px;
    top:600px;
    left: 950px;
    transform: translate(-50%,-55%);
    -webkit-animation: fadeout 2s;
    transition: 0.3s;
    /* animation-fill-mode: forwards; */
    /* animation-fill-mode: none; */
}

.login-head {
    position:absolute;
    font-size: 60px;
    left: 50%;
    top: 5%;
    transform:translate(-50%,-50%)
}

.login-form{
    /* background-color: red; */
    position:absolute;    
    width: 80%;
    top: 15%;
    left:50%;
    transform:translateX(-50%);
    text-align: left;    
    font-size: 30px;

    
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
    color:white;

}
.login-form > input[id='id']{
    margin-bottom: 8%;
}


#login-checkbox {
    position: absolute;
    text-align: left;    
    vertical-align: center;
    top:40%;
    left: 10%;
    width: 100%;

    
}

#login-checkbox > label {
    margin-right: 5px;
    
}

#login-checkbox > span {
    margin-left: 15%;
    cursor:pointer;
    transition: 0.3s;
}

#login-checkbox > span:hover {
    color:rgb(223, 247, 118)
}



.login-btn {
    position: absolute;
    width: 50%;
    height: 5%;
    top: 55%;
    left: 50%;
    color:white;
    background-color: rgb(102, 102, 245);
    border-radius: 10px;
    border: none;
    right:20%;
    transform:translateX(-50%)

}

.login-btn-sns {
    position: absolute;
    width: 50%;
    height: 5%;
    top: 65%;
    left: 50%;
    color:white;
    background-color: rgb(21, 255, 0);
    border-radius: 10px;
    border: none;
    right:20%;
    transform:translateX(-50%)

}

</style>
