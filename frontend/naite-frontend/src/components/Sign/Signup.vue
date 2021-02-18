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
                    <input id='idInput' type="text" v-model='params.userId' @keypress.space="checkSpace" @input='idComfirmMet' placeholder="5자 ~ 15자, 영어 및 숫자 함께 사용,한글 및 특수문자x">
                    <div :class='{fontRed:!idCheck,fontBlue:idCheck}'>{{idMessage}}</div>
                    <label for="#">비밀번호</label>
                    <input type="password" v-model='params.userPw' @input='checkPw' @keypress.enter="checkSpace" placeholder="8자이상, 영어/숫자/특수문자를 한개 이상 사용해주세요"><br>
                    <div :class='{fontRed:!pwCheck,fontBlue:pwCheck}'>{{pwMessage}}</div>
                    <label for="#">비밀번호확인</label>
                    <input type="password" v-model='pwConfirm' @keypress.space="checkSpace" ><br>   
                    <div>{{pwConfirmMessage}}</div>
                    <label for="#">닉네임</label>
                    <input id='nickInput' type="text"  v-model='params.userNick' @keypress.space="checkSpace" @keypress.enter='nickemailComfirmMet'><button  v-if='!nickConfirm' class="nickConfirm" @click='nickemailComfirmMet'>중복확인</button><br>   
                    <div>{{nickMessage}}</div>
                    <label for="#">프로필사진</label>
                    <label for="signupPic" class='signupPicForm' v-if='beforeSelectPic'>이미지 선택</label><br v-if='beforeSelectPic'>
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
                    <input type="text"  v-model='params.userName' @keypress.space="checkSpace"><br>                
                    <label for="#">이메일</label>
                    <input class='emailInput' type="text"  v-model='params.userEmail' @keypress.space="checkSpace" @keypress.enter='emailComfirm'><button  v-if='!emailConfirm' class="emailSend" @click='emailComfirm'>메일전송</button><br>            
                    <div>{{emailMessage}}</div>
                    <label for="#">이메일인증</label>
                    <input id='emailConfirmInput' type="text"  v-model='userEmailConfirm' @keypress.space="checkSpace" @keypress.enter='emailConfirmSend'><button v-if='!emailConfirm' class="emailConfirmSend" @click='emailMatching'>인증요청</button><br>            
                    <div>{{emailConfirmMessage}}</div>
                    <label for="#">주소</label>
                    <input type="text" 
                    placeholder="클릭하여 주소를 검색해주세요" 
                    v-model='params.userBasicAddress'
                    @click='openSearchLocation' 
                    readonly/>                 
                    <label for="#">상세주소</label>
                    <input type="text"  v-model='params.userDetailAddress'/>   
                    <button class="signupComplete" @click='completeSignup'>회원가입</button>
                    <div>{{signupMessage}}</div>

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
var check_spc = /[~!@#$%^&*()_+|<>?:{}]/;
var check_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

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
            idCheck:true,
            pwMessage:'',
            pwCheck:true,
            pwConfirmMessage:'',
            nickMessage:'',
            emailMessage:'',
            emailConfirmMessage:'',
            signupMessage:'',
        }
    },
    methods:{
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
            console.log(signupForm)
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
        idComfirmMet:function(){
            this.idMessage=''
            this.idCheck = true
            this.idConfirm = false
            if (!this.params.userId){
                this.idCheck = false
                this.idMessage = '아이디를 입력해주세요.'
                
                return;
            } else if (this.params.userId.length < 5 || this.params.userId.length>15 ){
                this.idCheck = false
                this.idMessage = '아이디 길이를 확인해주세요. *최소5자 ~ 최대 15자*'
                
                return;
            } else if (check_spc.test(this.params.userId || check_kor.test(this.params.userId))){
                this.idCheck = false
                this.idMessage = '특수문자 및 한글은 사용불가능합니다.'
                
                return;
            } else if (!check_eng.test(this.params.userId || !check_num.test(this.params.userId))){
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
                        this.params.userId = ''
                    } else {
                        this.idConfirm = true
                        this.idCheck = true
                        setTimeout(() => {
                            this.idMessage = res.data.message
                        }, 100);
                    }
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        checkPw:function(event){

            this.params.userPw = event.target.value
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
        nickemailComfirmMet:function(){
            axios.post(`${SERVER_URL}/user/sign/nick/${this.params.userNick}`)
                .then(res => {
                    if(res.data.response==='error'){
                        alert(res.data.message)
                    } else {
                        
                        this.nickConfirm = true
                    }
                })
                .catch(err=>{
                    console.log(err)
                })
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
            const params = {
                'type':0,                
                'userEmail':this.params.userEmail,
                'userId': this.params.userId,
            }
            alert('메일을 보내는중입니다.')
            axios.post(`${SERVER_URL}/user/sign/email/send`,params)
                .then(res => {   
                    if (res.data.response ==='error'){
                        alert(res.data.message)
                    } else {

                        alert('메일을 확인해주세요!')
                    }
                    
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
                'type':0,
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
                if (key!='userPic' &&!this.params[key]) {
                    nullCheck = false
                }
            }
            if (this.params.userPw === this.pwConfirm) {
                pwCheck = true
            }
        
            if(!this.idConfirm || !this.nickConfirm){
                alert('아이디 및 닉네임 중복확인을 진행해주세요')
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
    background-color: rgba(255, 0, 0, 0.411);
    color:white;
}


.signUpForm{
    position:absolute;
    top:50%;
    left:50%;
    width: 60%;
    height:80%;
    background-color: rgba(0, 0, 255, 0.37);
    transform: translate(-50%,-50%);

}

.signUpForm > header {
    margin-bottom: 2%;
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
}

.signUpForm>body>div {
    width:50%;
    font-family: font1;
    padding:1%;
    color:white;
    background-color: tomato;
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
.signUpForm>body>div:nth-child(2)>button:nth-of-type(3){
    display:block;
    border:none;
    outline:none;
    width:70%;
    border-radius: 10px;
    padding:5px 10px;
    font-size: 14px;
    color:white;
    background-color: rgba(161, 230, 34, 0.637);
}
.signUpForm>body>div>div {
    margin-top: -5%;
    margin-bottom:5%;
    font-size: 14px;
    width:70%;
    text-align-last: right;
    transition:0.5s;   
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
    color:blue
}

</style>
