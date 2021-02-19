<template>
  <div id="signup">               
        <div class="signUpForm">
            <header>
                <button class='backBtn' @click='toLogin'><i class="fas fa-arrow-circle-left"></i><span>뒤로가기</span></button> 
                <h2>회원가입</h2>
            </header>
            <body>
                <div>
                    <label for="#" >아이디</label>
                    <input id='idInput' type="text" @keypress.space="checkSpace" @input='idComfirmMet' placeholder="5자 ~ 15자, 영어 및 숫자 함께 사용,한글 및 특수문자x">
                    <div :class='{fontRed:!idCheck,fontBlue:idCheck}'>{{idMessage}}</div>
                    <label for="#">비밀번호</label>
                    <input type="password"  @input='checkPw' @keypress.enter="checkSpace" placeholder="8자이상, 영어/숫자/특수문자를 한개 이상 사용해주세요"><br>
                    <div :class='{fontRed:!pwCheck,fontBlue:pwCheck}'>{{pwMessage}}</div>
                    <label for="#">비밀번호확인</label>
                    <input type="password" @input='checkPwConfirm' @keypress.space="checkSpace" placeholder='비밀번호를 확인해주세요' ><br>   
                    <div :class='{fontRed:!pwConfirmCheck,fontBlue:pwConfirmCheck}'>{{pwConfirmMessage}}</div>
                    <label for="#">닉네임</label>
                    <input id='nickInput' type="text" @keypress.space="checkSpace" @input='nickemailComfirmMet' placeholder="5자 이내의 닉네임을 생성해주세요">   
                    <div :class='{fontRed:!userNickCheck,fontBlue:userNickCheck}'>{{nickMessage}}</div>
                    <label for="signupPic" class='signupPicForm' v-if='beforeSelectPic'>프로필 이미지</label><br v-if='beforeSelectPic'>
                    <input 
                    type="file" 
                    id='signupPic' 
                    name='files' 
                    @change='saveFile'                
                    >
                    <ProfilePreview :profileImg = 'userPic' :beforeSelectPic='beforeSelectPic' @resettingPic='resettingPic'/>
                </div>
                <div>
                    <label for="#" >이름</label>
                    <input type="text"  @keypress.space="checkSpace" @input='inputName' placeholder="한글이름을 입력해주세요"><br>
                    <div :class='{fontRed:!nameCheck,fontBlue:nameCheck}'>{{nameMessage}}</div>    

                    <label for="#">이메일</label>
                    <input class='emailInput' type="text"  v-model='params.userEmail' @keypress.space="checkSpace" @keypress.enter='emailComfirm' placeholder="ex) naite402@gmail.com">
                    <button  v-if='!emailConfirm' @click='emailComfirm'>메일전송</button><br>            
                    <div :class='{fontRed:!emailMessageCheck,fontBlue:emailMessageCheck}'>{{emailMessage}}</div>

                    <label for="#">이메일인증</label>
                    <input id='emailConfirmInput' type="text" @keypress.space="checkSpace" @input='emailMatching'>
                    <div :class='{fontRed:!emailConfirmCheck,fontBlue:emailConfirmCheck}'>{{emailConfirmMessage}}</div>

                    <label for="#">주소</label>
                    <input type="text" 
                    placeholder="클릭하여 주소를 검색해주세요" 
                    v-model='params.userBasicAddress'
                    @click='openSearchLocation' 
                    readonly/>                 
                    <label for="#">상세주소</label>
                    <input type="text"  v-model='params.userDetailAddress'/>   
                    <button class="signupComplete" @click='completeSignup' @mouseover="signupbtnhover" @mouseout="signupbtnmove">회원가입</button>
                    <div :class='{fontRed:!signupCheck,fontBlue:signupCheck}'>{{signupMessage}}</div>

                </div>
                
            </body>
        </div>
        <Location :searchLocation='searchLocation' @selectAddress = 'selectAddress' @checkAddress='checkAddress' />
  </div>
</template>

<script>
import Location from '@/components/Sign/Location'
import ProfilePreview from '@/components/Sign/ProfilePreview'


import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'
var check_num = /[0-9]/;
var check_eng = /[a-zA-Z]/;
var check_spc = /[~!@#$%^&*()_+./,';|<>?:{}"-]/;
var check_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
var check_email = /[@]/;

export default {
    name:'Signup',
    components:{
        Location,                
        ProfilePreview,
    },
    data: function() {
        return {
            searchLocation:false,
            params: {
                "userBasicAddress": "",
                "userDetailAddress": "",
                'userDong':'',
                "userEmail": "",
                "userId": "",
                "userName": "",
                "userNick": "",
                "userPw": ""
            },
            pwConfirm:'',
            emailConfirm:false,
            userEmailConfirm:'',
            addressConfirm: false,
            idConfirm:false,
            nickConfirm:false,
            userPic:'',
            beforeSelectPic:true,

            idMessage:'',
            idCheck:false,
            pwMessage:'',
            pwCheck:false,
            pwConfirmMessage:'',
            pwConfirmCheck:false,
            nickMessage:'',
            nameMessage:'',
            nameCheck:false,
            userNickCheck:false,
            emailMessage:'',
            emailMessageCheck:false,
            emailConfirmMessage:'',
            emailConfirmCheck:false,
            signupMessage:'',
            signupCheck:false,
        }
    },
    methods:{
        signupbtnhover:function(){
            this.signupCheck = true
            this.signupMessage = '안녕하세요! 나이테입니다'
            document.querySelector('.signUpForm>body>div:nth-child(2)>button:nth-last-child(2)').style.backgroundColor = 'rgb(161, 230, 34)'
        },
        signupbtnmove:function(){
            this.signupCheck = false
            this.signupMessage = ''
            document.querySelector('.signUpForm>body>div:nth-child(2)>button:nth-last-child(2)').style.backgroundColor = 'rgba(161, 230, 34, 0.637)'
        },
        test:function(){
            const list = ["userBasicAddress","userDetailAddress",'userDong',"userEmail","userId","userName","userNick","userPic","userPw"]
            const signupForm = document.createElement('form')
            
            for (let i =0;i<9;i++){
                if (i===7){ 
                    var addPic = document.querySelector('.testff')
                    signupForm.appendChild(addPic)
                    

                } else {
                    var addInput = document.createElement('input')
                    addInput.value = this.params[`${list[i]}`]
                    addInput.name = `${list[i]}`
                    signupForm.appendChild(addInput)
                }
            }
            signupForm.enctype='multipart/form-data'
            signupForm.method = 'post'
            signupForm.action=`${SERVER_URL}/user/sign/signup`
            document.body.appendChild(signupForm)
            signupForm.submit()
        },
        toLogin:function(){
            this.$emit('changeLogin')    
            for (let key in this.params) {
                this.params[key] = ''
            }   
            const showSignup = document.getElementById('signup')
            showSignup.style.webkitAnimationName = 'fadeout'
            showSignup.style.webkitAnimationDuration='0s'
        },
        idComfirmMet:function(event){
            this.params.userId = event.target.value
            this.idCheck = false
            this.idConfirm = false
            if (!this.params.userId){
                this.idCheck = false
                this.idMessage = '아이디를 입력해주세요.'
                
                return;
            } else if (this.params.userId.length < 5 || this.params.userId.length>15 ){
                this.idCheck = false
                this.idMessage = '아이디 길이를 확인해주세요. *최소5자 ~ 최대 15자*'
                
                return;
            } else if (check_spc.test(this.params.userId) || check_kor.test(this.params.userId)){
                this.idCheck = false
                this.idMessage = '특수문자 및 한글은 사용불가능합니다.'
                
                return;
            } else if (!check_eng.test(this.params.userId) || !check_num.test(this.params.userId)){
                this.idCheck = false
                this.idMessage = '영어와 숫자를 함께 사용하여 아이디를 생성해주세요.'
                
                return;
            } else {
                this.idCheck = true
                this.idMessage = '아이디 인증 중...'
            }
            axios.post(`${SERVER_URL}/user/sign/id/${this.params.userId}`)
                .then(res=>{
                    if(res.data.response==='error'){
                        this.idCheck = false
                        this.idMessage = res.data.message
                    } else {
                        this.idConfirm = true
                        this.idCheck = true
                        this.idConfirm = true
                        this.idMessage = res.data.message
                        
                    }
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        checkPw:function(event){
            this.params.userPw = event.target.value
            this.pwCheck = false
            if (!check_eng.test(this.params.userPw) || !check_num.test(this.params.userPw) || !check_spc.test(this.params.userPw)){
                this.pwMessage = '특수문자/영어/숫자를 모두 입력해주세요.'
                this.pwCheck = false
            } else if(this.params.userPw.length <8){
                this.pwMessage = '8자 이상 입력해주세요!'
                this.pwCheck = false
            } else {
                this.pwMessage = '확인되었습니다. 회원가입을 진행해주세요'
                this.pwCheck = true
            }
        },
        checkPwConfirm:function(event){
            this.pwConfirm=event.target.value
            this.pwConfirmCheck=false
            if (this.pwConfirm !== this.params.userPw){
                this.pwConfirmMessage = '비밀번호를 일치시켜주세요'
                this.pwConfirmCheck = false
            } else if (!this.pwCheck){
                this.pwConfirmMessage = '비밀번호 양식을 맞춰주세요'
                this.pwConfirmCheck = false
            }  else {
                this.pwConfirmCheck=true
                this.pwConfirmMessage = '비밀번호가 일치합니다. 회원가입을 진행해주세요'
            }
        },
        nickemailComfirmMet:function(event){
            this.params.userNick=event.target.value
            this.userNickCheck = false
            this.nickConfirm = false
            if (this.params.userNick.length < 1){
                this.userNickCheck = false
                this.nickMessage = '1자이상의 닉네임을 작성해주세요'
                return 
            }
            var getTextLength = function(str) {
                var len = 0;
                for (var i = 0; i < str.length; i++) {
                    len++;
                }
                return len;
            }
            

            if (getTextLength(this.params.userNick)>5) {
                this.userNickCheck = false
                this.nickMessage = '5자이내의 닉네임을 작성해주세요'
                return 
            }
            axios.post(`${SERVER_URL}/user/sign/nick/${this.params.userNick}`)
                .then(res => {
                    if(res.data.response==='error'){
                        this.userNickCheck = false
                        this.nickMessage = res.data.message
                    } else {
                        this.userNickCheck = true
                        this.nickMessage = res.data.message
                        this.nickConfirm = true
                    }
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        inputName:function(event){
            this.params.userName = event.target.value
            this.nameCheck = false
            var getTextLength = function(str) {
                var len = 0;
                for (var i = 0; i < str.length; i++) {
                    len++;
                }
                return len;
            }
            if (getTextLength(this.params.userName ) < 2){
                this.nameMessage = '이름을 입력해주세요'
                this.nameCheck = false
            } else if (check_num.test(this.params.userName) || check_spc.test(this.params.userName)){
                this.nameMessage = '특수 문자 및 숫자를 사용할 수 없습니다.'
                this.nameCheck = false
            } else if (check_eng.test(this.params.userName)){
                this.nameMessage = '한국이름을 입력해주세요'
                this.nameCheck = false
            } else {
                this.nameMessage = `${this.params.userName}님 나이테오신 것을 환영합니다^^`
                this.nameCheck=true
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
            this.params.userDong = result.bname2
        },
        emailComfirm:function(){
            this.emailMessageCheck = false
            if (!check_email.test(this.params.userEmail)||check_kor.test(this.params.userEmail)){
                this.emailMessage = '이메일 형식에 맞춰주세요'
                this.emailMessageCheck = false
                return
            }
            const params = {
                'type':0,                
                'userEmail':this.params.userEmail,
                'userId': this.params.userId,
            }
            this.emailMessage = '이메일을 보내는중입니다. 잠시만 기다려주세요'
            this.emailMessageCheck = true
            axios.post(`${SERVER_URL}/user/sign/email/send`,params)
                .then(res => {   
                    if (res.data.response ==='error'){
                        this.emailMessage = res.data.message
                        this.emailMessageCheck = false
                    } else {
                        this.emailMessage = '메일이 전송되었습니다 메일을 확인해주세요'
                        this.emailMessageCheck = true
                    }
                    
                })
                .catch(() => {
                    this.emailMessage = '메일 전송 중 오류가 발생하였습니다.관리자에게 문의해주세요'
                    this.emailMessageCheck = false
                })
        },
        checkAddress:function(res) {
            this.addressConfirm = res          
            // this.addressConfirm = true       
        },
        checkSpace: function(){
            event.returnValue = false;
        },
        emailMatching:function(event){
            this.userEmailConfirm=event.target.value
            this.emailConfirmCheck = false
            
            if (check_eng.test(this.userEmailConfirm)||check_spc.test(this.userEmailConfirm)||check_kor.test(this.userEmailConfirm)){
                this.emailConfirmMessage = '숫자만 입력 가능합니다'
                this.emailConfirmCheck = false
                return
            }

            const params = {
                'email':this.params.userEmail,
                'key':this.userEmailConfirm,
                'type':0,
            }
            axios.get(`${SERVER_URL}/user/sign/email/auth`,{params})
                .then(res => {
                    if (res.data.response==='error') {
                        
                        this.emailConfirmMessage = res.data.message
                        this.emailConfirmCheck = false
                    } else {
                        this.emailConfirm = true
                        this.emailConfirmMessage = '이메일 인증이 완료되었습니다. 회원가입을 진행해주세요'
                        this.emailConfirmCheck = true
                    }                   
                })
                .catch(err => {
                    this.emailConfirmMessage = err.data.message
                    this.emailConfirmCheck = false
                })
        },
        completeSignup:function(){  
            let nullCheck = true
            let pwCheck = false
            for (let key in this.params) {
                if (key!='userPic' &&!this.params[key]) {
                    nullCheck = false
                }
            }            
            if (this.params.userPw === this.pwConfirm) {
                pwCheck = true
            }
            if(!this.idConfirm || !this.nickConfirm){
                this.signupCheck = false
                this.signupMessage ='아이디 및 닉네임 중복확인을 진행해주세요'
                
            }  else if (!pwCheck) {
                this.signupCheck = false
                this.signupMessage ='비밀번호를 확인해주세요!'
            } else if (!nullCheck) {
                this.signupCheck = false
                this.signupMessage ='모든 정보를 기입해주세요!'
                
            } else if (!this.emailConfirm) {
                this.signupCheck = false
                this.signupMessage ='이메일인증을 진행해주세요!'
                
            } else if (!this.addressConfirm) {
                this.signupCheck = false
                this.signupMessage ='주소를 정확히 입력해주세요!'                
            } else if (pwCheck&&nullCheck&&this.emailConfirm&&this.addressConfirm) {
                let signupFormData = new FormData()
                const paramsKey =["userBasicAddress","userDetailAddress",'userDong',"userEmail","userId","userName","userNick","userPw"]
                for (let i=0;i<8;i++){
                    signupFormData.append(`${paramsKey[i]}`,this.params[`${paramsKey[i]}`])                    
                }

                if(this.userPic){
                    signupFormData.append('files',this.userPic)
                }
                axios.post(`${SERVER_URL}/user/sign/signup`,
                    signupFormData, {
                        headers: {
                            'Content-type':'multipart/form-data',
                            }
                        }
                    
                ).then(res=>{
                    if (res.data.response==='success'){
                            this.signupCheck = true
                            this.signupMessage ='나이테에 오신걸 환영합니다. 이웃과 소통을 통해 나이테를 즐겨주세요^^'
                            setTimeout(() => {
                                this.toLogin()
                            }, 500);
                        } else {
                            this.signupCheck = false
                            this.signupMessage =res.data.message
                        }
                })
                .catch(err=>{
                    console.log(err)
                })

            }
        },
        saveFile:function(e){
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length) {
                this.userPic = ''
                return;
            }
            this.userPic = files[0]
            this.beforeSelectPic = false
        },
        resettingPic:function(){
            var picInput = document.getElementById('signupPic')
            picInput.value = ''
            this.beforeSelectPic = true
            this.userPic = ''

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
                // showSignup.style.top = '0%'
                showSignup.style.webkitAnimationName = 'fadein'
                showSignup.style.webkitAnimationDuration='1s'
            } else {
                showSignup.style.webkitAnimationName = 'fadeout'
                showSignup.style.webkitAnimationDuration='0s'
                // showSignup.style.top = '-200%'
            }
        },
        'params.userId':function(){
            if (this.idConfirm) {
                this.idConfirm = false
                document.getElementById('idInput').style.width='70%'
            }
        },
        'params.userNick':function(){
            if(this.nickConfirm){
                this.nickConfirm = false
                document.getElementById('nickInput').style.width='70%'
            }
        }
    }
}
</script>



<style scoped>

::placeholder {
    color: rgba(255, 255, 255, 0.6)
}

@keyframes textEffect{
    0% {
        margin-right: 5%;
    }
    33% {
        margin-right: 4%;
    }
    66% {
        margin-right: 6%;
    }
    100%{
        margin-right: 5%;
    }
}



#signup {
    position: absolute;
    height: 100%;
    top:0%;
    width:100%;      
    transition: 0.3s;  
    font-family: font1;
    visibility: hidden;
    animation-fill-mode: forwards;
    color:white;
}


.signUpForm{
    position:absolute;
    top:55%;
    left:50%;
    width: 60%;
    height:80%;
    transform: translate(-50%,-50%);

}

.signUpForm > header {
    margin-bottom: 2%;
    margin-right: 12%;
}

.signUpForm>header>button {
    position:absolute;
    text-align: left;
    left:0;
    border:none;
    outline: none;
    background-color: transparent;
    font-family: font1;
    color:white;
    width:100%;
    transition: 0.3s;
}
.signUpForm>header>button>span{
    margin-left: 1%;
    white-space: nowrap;
    
}
.signUpForm>header>button:hover{
    font-size: 20px;
    left:-3%;
}

.signUpForm>body {
    display:flex;
    height: 92%;
    background-color: transparent;
}

.signUpForm>body>div {
    width:50%;
    font-family: font1;
    padding:1%;
    color:white;
    /* background-color: tomato; */
    background-color: transparent;
    height:100%;
   
}

.signUpForm>body>div>label{
    display:flex;
    align-items:flex-end;
    height:5%;
    margin:0;
    font-size: 20px;
    
}
.signUpForm>body>div>input {
    height: 5%;
    margin-bottom: 5%;
    margin-right: 5%;
    width:70%;
    border: none;
    background-color: transparent;
    outline: none;
    border-bottom: 1px solid white;
    color:white;
    transition:0.3s;
}
.signUpForm>body>div>button{
    border:none;
    outline:none;
    border-radius: 10px;
    padding:5px 10px;
    font-size: 14px;
    color:white;
    background-color: rgba(0, 0, 0, 0.664);  
    transition:0.3s;
}
.signUpForm>body>div:nth-child(2)>button:nth-last-child(2){
    display:block;
    border:none;
    outline:none;
    width:70%;
    border-radius: 10px;
    padding:5px 10px;
    font-size: 14px;
    color:white;
    transition:0.3s;
    background-color: rgba(161, 230, 34, 0.637);
}
.signUpForm>body>div>div {
    margin-top: -5%;
    margin-bottom:5%;
    font-size: 14px;
    width:70%;
    text-align-last: right;
}
.signUpForm>body>div:nth-child(2)>div:nth-last-child(1) {
    margin-top: 0%;
    margin-bottom:5%;
    font-size: 14px;   
}
.fontRed {
    color:red
}

.fontBlue {
    color:rgb(0, 255, 0);
}

@media screen and (max-width:501px) {
    
    /* #signup {
        position: absolute;
        height: 100%;
        top:0%;
        width:100%;      
        transition: 0.3s;  
        font-family: font1;
        visibility: hidden;
        animation-fill-mode: forwards;
        color:white;
    } */


    .signUpForm{
        position:absolute;
        top:5%;
        left:50%;
        width: 90%;
        height:90%;
        transform: translateX(-50%);        
        overflow: auto;

    }

    .signUpForm > header {
        margin-right: 0%;
        font-size: 15px;
        margin-bottom: 5%;
    }
    .signUpForm > header > h2 {
        font-size: 22px;
    }

    .signUpForm>body {
        display:block;
        height: 92%;
    }

    .signUpForm>body>div {
        width:100%;
        color:white;
        /* background-color: tomato; */
        background-color: transparent;
        height:100%;
    
    }
    .signUpForm>body>div:nth-child(1){
        height:80%
    }
    .signUpForm>body>div>label{
        display:flex;
        align-items:flex-end;
        height:5%;
        margin:0;
        font-size: 15px;
        
    }
    .signUpForm>body>div>input {
        height: 5%;
        margin-bottom: 5%;
        margin-right: 5%;
        width:90%;
        border: none;
        background-color: transparent;
        outline: none;
        border-bottom: 1px solid white;
        color:white;
        transition:0.3s;
        font-size: 12px;
    }
    .signUpForm>body>div>button{
        border:none;
        outline:none;
        border-radius: 10px;
        padding:5px 10px;
        font-size: 14px;
        color:white;
        background-color: rgba(0, 0, 0, 0.664);  
        transition:0.3s;
    }
    .signUpForm>body>div:nth-child(2)>button:nth-last-child(2){
        display:block;
        border:none;
        outline:none;
        width:100%;
        border-radius: 10px;
        padding:5px 10px;
        font-size: 14px;
        color:white;
        transition:0.3s;
        background-color: rgba(161, 230, 34, 0.637);
    }
    .signUpForm>body>div>div {
        margin-top: -5%;
        margin-bottom:5%;
        font-size: 10px;
        width:90%;
        text-align-last: right;
    }
    .signUpForm>body>div:nth-child(2)>div:nth-last-child(1) {
        margin-top: 0%;
        margin-bottom:5%;
        font-size: 14px;   
    }
    .fontRed {
        color:red
    }

    .fontBlue {
        color:rgb(0, 255, 0);
    }
    
}

</style>
