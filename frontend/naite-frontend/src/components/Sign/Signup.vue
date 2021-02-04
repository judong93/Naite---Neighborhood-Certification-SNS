<template>
  <div id="signup">       
        <div class="signupform">
            <div class='signuphead'>회원가입</div>
            <div class="signupleftdiv">
                <label for="#" >아이디</label><br>
                <input type="text" v-model='params.userId' @keypress.space="checkSpace"><br>
                <label for="#">비밀번호</label><br>
                <input type="password" v-model='params.userPw' @keypress.space="checkSpace"><br>
                <label for="#">비밀번호확인</label><br>
                <input type="password" v-model='pwConfirm' @keypress.space="checkSpace"><br>   
                <label for="#">닉네임</label><br>
                <input type="text"  v-model='params.userNick' @keypress.space="checkSpace"><br>   
                <label for="#">프로필사진</label><br>
                <input type="text"  v-model='params.userPic' @keypress.space="checkSpace"><br>                
            </div>
            <div class="signuprightdiv">
                <label for="#" >이름</label><br>
                <input type="text"  v-model='params.userName' @keypress.space="checkSpace"><br>                
                <label for="#">이메일</label><br>
                <input class='emailInput' type="text"  v-model='params.userEmail' @keypress.space="checkSpace"><button  v-if='!emailConfirm' class="emailSend" @click='emailComfirm'>인증메일보내기</button><br>            
                <label for="#">이메일인증</label><br>
                <input class='emailInput' type="text"  v-model='userEmailConfirm' @keypress.space="checkSpace"><button v-if='!emailConfirm' class="emailSend" @click='emailMatching'>인증요청보내기</button><br>            
                <label for="#">주소</label><br>
                <input type="text" 
                placeholder="클릭하여 주소를 검색해주세요" 
                v-model='params.userBasicAddress'
                @click='openSearchLocation' 
                readonly/> <br>                
                <label for="#">상세주소</label><br>
                <input type="text"  v-model='params.userDetailAddress'/><br>   
                <button class="signupComplete" @click='completeSignup'>회원가입</button>
            </div>
        </div>
        <button @click='toLogin'>test: back to login or complete signup</button>

        <Location :searchLocation='searchLocation' @selectAddress = 'selectAddress' @checkAddress='checkAddress' />
        


       
  </div>
</template>

<script>
import Location from '@/components/Sign/Location'
import axios from 'axios'

const SERVER_URL = 'http://localhost:8080'

export default {
    name:'Signup',
    components:{
        Location,                
    },
    data: function() {
        return {
            searchLocation:false,
            params: {
                "userBasicAddress": "",
                "userDetailAddress": "",
                "userEmail": "",
                "userId": "",
                "userName": "",
                "userNick": "",
                "userPic": "",
                "userPw": ""
            },
            pwConfirm:'',
            emailConfirm:false,
            userEmailConfirm:'',
            addressConfirm: false,
        }
    },
    methods:{
        toLogin:function(){
            this.$emit('changeLogin')    
            for (let key in this.params) {
                this.params[key] = ''
            }   
        },
        openSearchLocation:function(){
            if (this.searchLocation) {
                this.searchLocation = false
            } else {
                this.searchLocation=true
            }
        },
        selectAddress:function(result) {
            this.searchLocation = false
            this.params.userBasicAddress = result.address
        },
        emailComfirm:function(){
            const params = {
                'type':0,
                'userEmail':this.params.userEmail
            }
            console.log(params)
            axios.post(`${SERVER_URL}/user/sign/email/send`,params)
                .then(res => {
                    console.log(res)
                    alert('메일을 확인해주세요!')
                    
                })
                .catch(err => {
                    console.log(err)
                })
        },
        checkAddress:function(res) {
            this.addressConfirm = res
        },
        checkSpace: function(){
            event.returnValue = false;
        },
        emailMatching:function(){
            const params = {
                'email':this.params.userEmail,
                'key':this.userEmailConfirm,
            }
            axios.get(`${SERVER_URL}/user/sign/email/auth`,{params})
                .then(res => {
                    if (res.data.response==='error') {
                        alert(res.data.message)
                    } else {
                        this.emailConfirm = true
                        alert('이메일 인증이 완료되었습니다!')
                    }
                    console.log(res.data)
                    
                })
                .catch(err => {
                    alert(err.data.message)
                })
        },
        completeSignup:function(){  
            let nullCheck = true
            let pwCheck = false
            for (let key in this.params) {
                if (!this.params[key]) {
                    nullCheck = false
                }
            }
            if (this.params.userPw === this.pwConfirm) {
                pwCheck = true
            }

            if (pwCheck&&nullCheck&&this.emailConfirm&&this.addressConfirm) {
                
                axios.post(`${SERVER_URL}/user/sign/signup`,this.params)
                    .then(res => {
                        if (res.data.response==='success'){
                            alert('회원가입이 완료되었습니다!')    
                            this.toLogin()
                        } else {
                            alert(res.data.message)
                        }
                        
                    })
                    .catch(err=>{
                        console.log(err)
                    })

            } else if (!pwCheck) {
                alert('비밀번호를 확인해주세요!')
            } else if (!nullCheck) {
                alert('모든 정보를 기입해주세요!')
            } else if (!this.emailConfirm) {
                alert('이메일인증을 진행해주세요!')
            } else if (!this.addressConfirm) {
                alert('주소를 정확히 입력해주세요!')
            }
            
            




        },
  
      
    },
    computed: {

    },
    props:{
        toSignup:Boolean,
    },
    watch:{
        toSignup:function(){
            const showSignup = document.getElementById('signup')
            if (this.toSignup){
                showSignup.style.top = '0%'
            } else {
                showSignup.style.top = '-100%'
            }
        },
        emailConfirm:function(){
            const inputBox = document.querySelectorAll('.emailInput')
            if (this.emailConfirm) {
                inputBox.forEach(function(box){
                    box.style.width='80%'
                })
                
            } else {
                inputBox.forEach(function(box){
                    box.style.width='65%'
                })
            }
        }
    }
}
</script>



<style scoped>

::placeholder {
    color: rgba(255, 255, 255, 0.6)
}


#signup {
    position: absolute;
    height: 100%;
    top:-100%;
    width:100%;      
    transition: 0.3s;  
    font-family: font1;
}
.signuphead {
    position: absolute;
    left: 50%;
    top: 5%;
    font-size: 60px;
    color:white;
    transform: translate(-50%,-50%);


}
.signupform {
    position: absolute;
    width: 1200px;
    height: 900px;
    top:50%;
    left:50%;
    transform:translate(-50%,-50%);
    display:flex;
    
    font-size: 30px;
    color:white;
    
}

.signupleftdiv{    
    width: 50%;
    height: 80%;
    margin-top: 10%;
    text-align: left;
 
}
.signuprightdiv{   
    width: 50%;
    height: 80%;
    margin-top: 10%;
    text-align: left; 
}

.signuprightdiv>input{
    text-align: left;
    
}



.signupform input {
    width: 80%;
    height: 5%;
    border-style: dotted;
    border-top: none;
    border-left: none;
    border-right: none;
    outline: none;
    background-color: transparent;
    color:white
}
.signuprightdiv > .emailInput{
    width: 65%;
    
}


.signupform label {
    margin-top:5%;
}

.signupComplete{
    margin-top: 10%;
    background-color: cornflowerblue;
    color:white;
    border:none;
    border-radius: 10px;
    cursor:pointer;
}


.emailSend {
    font-size: 15px;
    border:none;
    border-radius: 10px;
    height:5%;    
}   



button {
    z-index: 100;
}



</style>
