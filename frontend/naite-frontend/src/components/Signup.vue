<template>
  <div id="signup">       
        <div class="signupform">
            <div class='signuphead'>회원가입</div>
            <div class="signupleftdiv">
                <label for="#" >아이디</label><br>
                <input type="text" v-model='params.userId'><br>
                <label for="#">비밀번호</label><br>
                <input type="password" v-model='params.userPw'><br>
                <label for="#">비밀번호확인</label><br>
                <input type="password" v-model='pwConfirm'><br>   
                <label for="#">닉네임</label><br>
                <input type="text"  v-model='params.userNick'><br>   
                <label for="#">프로필사진</label><br>
                <input type="text"  v-model='params.userPic'><br>                
            </div>
            <div class="signuprightdiv">
                <label for="#">이름</label><br>
                <input type="text"  v-model='params.userName'><br>                
                <label for="#">이메일</label><br>
                <input type="text"  v-model='params.userEmail'><br>            
                <label for="#">주소</label><br>
                <input type="text" 
                placeholder="클릭하여 주소를 검색해주세요" 
                v-model='params.userBasicAddress'
                @click='openSearchLocation' 
                readonly/><br>                
                <label for="#">상세주소</label><br>
                <input type="text"  v-model='params.userDetailAddress'/><br>   
                <button class="signupComplete" @click='completeSignup'>회원가입</button>
                <button @click='toLogin'>test: back to login or complete signup</button>
                <button class="testem" @click='test'>testemailconfirm</button>
            </div>
        </div>

        <Location :searchLocation='searchLocation' @selectAddress = 'selectAddress'/>
        


       
  </div>
</template>

<script>
import Location from '@/components/Location'
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
            testparam:{
                'type':0,
                'userEmail':'ldh29768@gmail.com'
            }
        }
    },
    methods:{
        toLogin:function(){
            this.$emit('changeLogin')
            console.log('?')
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
        completeSignup:function(){
            axios.post(`${SERVER_URL}/signup`,this.params)
                .then(res => {
                    console.log(res)
                })
                .catch(err=>{console.log(err)})
        },
        test:function(){
            axios.post(`${SERVER_URL}/email/send`,this.testparam)
                .then(res => {
                    console.log(res)
                    
                })
                .catch(err => {console.log(err,'ㅠㅠ')})
        }
  
      
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
        }
    }
}
</script>



<style>

::placeholder {
    color: rgba(255,255,255,0.6)
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
    margin-top: 15%;
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


button {
    z-index: 100;
}



</style>
