<template>
  <div id="login">
    <div class="loginBox">
        <div class="login-head">LOGIN</div>

        <form action="#" class='login-form'>
            <label for="id">ID</label><br>
            <input type="text" 
            id='id' placeholder="아이디를 입력해주세요" 
            @keypress.space='checkSpace'
            v-model="params.userId"><br>
            <label for="password">PASSWORD</label><br>
            <input type="password" 
            id='password' 
            @keypress.enter="loginById" 
            @keypress.space='checkSpace'
            placeholder="비밀번호를 입력해주세요" 
            v-model="params.userPw"><br>
        </form>
        <div id='login-checkbox'>
            <label for="id-save">아이디 저장</label>
            <input type="checkbox" id='id-save'>
            <label for="auto-login" style='margin-left:3%;'>자동 로그인</label>
            <input type="checkbox" id='auto-login'>
            <span @click='look_signup'>Don't have an account? Signup</span>
        </div>
        
        <button class="login-btn" @click='loginById'>로그인</button>
        <button class="login-btn-sns">네이버로 로그인 하기</button>
        <span class='find-pw'>아이디/비밀번호 찾기</span>
        
    </div>
    
    


  </div>
</template>

<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'

const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'



export default {
    name:'Login',
    components:{
                
    },
    data: function() {
        return {
            params: {
                'userId':'',
                'userPw':'',
            }
        }
    },
    methods:{
        look_signup: function(){
            const loginDiv = document.getElementById('login')
            loginDiv.style.top = '200%'
            this.$emit('changeSignup')
        },
        checkSpace:function(){
            event.returnValue=false;
        },
        loginById:function(){
            if (this.params.userId && this.params.userPw) {
                axios.post(`${SERVER_URL}/user/sign/signin`,this.params)
                    .then(res=>{
                        const token = res.data['auth-token']
                        const decoded = jwt_decode(token)
                        this.$router.push({name:'Main'})
                        this.$store.dispatch('saveuserinfo',decoded)                        
                        localStorage.setItem('jwt',res.data['auth-token'])
                    })
                    .catch(err=>{
                        console.log(err)
                        alert('회원정보를 확인해주세요!')
                    })
            } else {
                alert('회원정보를 입력해주세요')
            }
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
                loginDiv.style.top = '0%'
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
    /* -webkit-animation: fadeout 2s; */
    transition: 0.3s;
    /* background-color: #3f9f4773; */
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
    color:rgb(208, 255, 0)
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

.find-pw{
    position:absolute;
    bottom: 20%;
    left: 50%;
    transform: translateX(-50%);
    cursor:pointer;
    border-bottom: white solid 1px;
}


</style>
