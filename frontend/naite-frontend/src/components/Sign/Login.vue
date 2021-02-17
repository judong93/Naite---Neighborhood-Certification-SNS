<template>
  <div id="login">   
    <div class="loginBox">
        <div class="login-head">
            LOGIN
        </div>

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
            <input type="checkbox" id='id-save' :checked='saveId'>
            <label for="auto-login" style='margin-left:3%;'>자동 로그인</label>
            <input type="checkbox" id='auto-login' :checked='autoLogin'>
            <span id='webSingup' @click='look_signup'>Don't have an account? Signup</span>
            
        </div>
        
        <button class="login-btn" @click='loginById'>로그인</button><br>
        <span class='find-pw' @click='switchFindLogin'>아이디/비밀번호 찾기</span><br>
        <span id='mobiSignup' @click='look_signup'>회원가입</span>
        <!-- <div class='loginlogo'>
            <img src="../../assets/naitelogo.png" alt="">
        </div> -->
    </div>

    
    


  </div>
</template>

<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'



export default {
    name:'Login',
    components:{
                
    },
    data: function() {
        return {
            params: {
                'userId':'',
                'userPw':'',
            },
            'saveId':false,
            'autoLogin':false,
        }
    },
    methods:{
        look_signup: function(){
            // const loginDiv = document.getElementById('login')
            // loginDiv.style.top = '200%'
            const btn = document.getElementById('login')
            btn.style.webkitAnimationName = 'fadeout'
            btn.style.webkitAnimationDuration='0s'
            this.$emit('changeSignup')
        },
        checkSpace:function(){
            event.returnValue=false;
        },
        loginById:function(){
            const saveId = document.querySelector('#id-save')
            const autoLogin = document.querySelector('#auto-login')
            
            if (this.params.userId && this.params.userPw) {
                axios.post(`${SERVER_URL}/user/sign/signin`,this.params)
                    .then(res=>{
                        const token = res.data['auth-token']
                        const decoded = jwt_decode(token)
                        this.$router.push({name:'Main'})
                        this.$store.dispatch('saveuserinfo',decoded)                        
                        localStorage.setItem('jwt',res.data['auth-token'])
                        
                        if (saveId.checked) {
                            this.$store.dispatch('saveId',this.params.userId)
                        } else {
                            this.$store.dispatch('saveId','')
                        }

                        if (autoLogin.checked) {
                            const param = {
                                'userId':this.params.userId,
                                'userPw':this.params.userPw
                            }
                            this.$store.dispatch('autologin',param)
                        } else {
                            this.$store.dispatch('autologin','')
                        }
                        const params = {
                            'saveId': saveId.checked,
                            'autologin':autoLogin.checked,
                        }
                        this.$store.dispatch('saveloginstate',params)


                    })
                    .catch(err=>{
                        console.log(err)
                        alert('회원정보를 확인해주세요!')
                    })
            } else {
                alert('회원정보를 입력해주세요')
            }
        },
        switchFindLogin:function(){
            const btn = document.getElementById('login')
            btn.style.webkitAnimationName = 'fadeout'
            btn.style.webkitAnimationDuration='0s'
            this.$emit('switchFindLogin')
        }
    },
    computed: {

    },
    props:{
        toLogin:Boolean,
        toFind:Boolean,
    },
    
    watch:{
        toLogin: function(){
            const btn = document.getElementById('login')
            if(this.toLogin){
                // const loginDiv = document.getElementById('login')
                // loginDiv.style.top = '0%'
                btn.style.webkitAnimationName = 'fadein'
                btn.style.webkitAnimationDuration='1s'
                // this.$emit('changeSignup')
            } 

        },
        toFind:function(){
            const btn = document.getElementById('login')
            if(!this.toFind){                
                btn.style.webkitAnimationName = 'fadein'
                btn.style.webkitAnimationDuration='1s'                
            } 

        }

    },
    created(){
        if (this.$store.state.loginState){
            this.saveId = this.$store.state.loginState.saveId
            this.autoLogin = this.$store.state.loginState.autologin
        }
        if (this.saveId) {
            this.params.userId = this.$store.state.savedId
        }
        if (this.autoLogin) {
            this.params.userId= this.$store.state.autoLogin.userId
            this.params.userPw= this.$store.state.autoLogin.userPw
            this.loginById()
        }     
    }
}
</script>



<style>
::placeholder {
    color: rgba(255,255,255,0.6)
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
    animation-fill-mode: forwards;
    overflow: hidden;
    /* background-color: red; */
    
    
}

.loginBox {
    position:relative;
    width: 700px;
    height: 1000px;
    top:600px;
    left: 950px;
    transform: translate(-50%,-55%);
    transition: 0.3s;
    overflow: hidden;
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
    bottom: 30%;
    left: 50%;
    transform: translateX(-50%);
    cursor:pointer;
    border-bottom: white solid 1px;
}

.loginlogo{
    position:absolute;
    bottom:15%;
    left:50%;
    transform: translate(-50%,-50%);
}

.loginlogo>img{
    width: 150px
}

#mobiSignup {
    display: none;
}

@media screen and (max-width: 1650px){
    #login{
        overflow: auto;
    }
}
@media screen and (max-width: 501px) {
    #login {
        overflow: hidden;
        width: 100vw;
        height: 100vh;
        color:white;
    }

    .loginBox{
        width: 100vw;
        height:100vh;
        transform: none;
        top:0;
        left:0;
    }

    .login-head {
        position:relative;
        font-size: 20px;
        margin-top: 20%;   
    }

    .login-form {
        position: relative;
        font-size: 15px;
        top:0;
        left:0;
        transform: none;
        margin: 20% auto 1%;
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
        border-radius: 5px;
        background-color: rgba(0,0,0,0.2);
        padding:0 5px;
        font-size: 15px;
        color:white;

    }
    
    #login-checkbox {
        position: relative;
        text-align: left;    
        vertical-align: center;
        top:0%;
        left: 0%;
        width: 100%;
        font-size: 10px;
        color:white;
    }

    #login-checkbox > label {
    margin-right: 1%;
        
    }
    #login-checkbox :nth-child(1){
        margin-left: 57%;
    }

    #webSingup{
        display: none;
    }

   

    .login-btn {
        position: relative;
        width: 50%;
        height: 5%;
        top: 0;
        left: 0;
        color:white;
        background-color: rgba(102, 102, 245);
        border-radius: 10px;
        border: none;
        right:0%;
        transform:none;
        margin-top: 5%;
        font-size: 15px;

    }

    .find-pw{
        position:relative;
        top: 5%;
        left: 0%;
        transform:none;
        cursor:pointer;
        border-bottom: white solid 1px;
        color:white;
        background-color: rgba(0,0,0,0.3);
        border-radius: 5px;
        padding:5px;
        font-size: 10px;        
    }
    
    #mobiSignup {
        position:relative;
        top:5%;
        font-size: 15px;
        display: block;
        color:white;
        background-color: rgba(0,0,0,0.3);
        width:20%;
        margin: 5% auto;
        border-radius: 5px;

    }


    
}

</style>
