<template>
  <div id="signup">               
        <div class="signupform">
            <div class='signuphead'>회원가입</div>
            <button class='backBtn' @click='toLogin'><i class="fas fa-arrow-circle-left"><span>뒤로가기</span></i></button> 
            <div class="signupleftdiv">
                <label for="#" >아이디</label><br>
                <input id='idInput' type="text" v-model='params.userId' @keypress.space="checkSpace" @keypress.enter='idComfirmMet'><button  v-if='!idConfirm' class="idConfirm" @click='idComfirmMet'>중복확인</button><br>
                <label for="#">비밀번호</label><br>
                <input type="password" v-model='params.userPw' @keypress.enter="checkSpace"><br>
                <label for="#">비밀번호확인</label><br>
                <input type="password" v-model='pwConfirm' @keypress.space="checkSpace"><br>   
                <label for="#">닉네임</label><br>
                <input id='nickInput' type="text"  v-model='params.userNick' @keypress.space="checkSpace" @keypress.enter='nickemailComfirmMet'><button  v-if='!nickConfirm' class="nickConfirm" @click='nickemailComfirmMet'>중복확인</button><br>   
                <label for="#">프로필사진</label><br>
                <label for="signupPic" class='signupPicForm' v-if='beforeSelectPic'>이미지 선택</label><br v-if='beforeSelectPic'>
                <input 
                type="file" 
                id='signupPic' 
                name='files' 
                @change='saveFile'                
                >
                <ProfilePreview :profileImg = 'userPic' :beforeSelectPic='beforeSelectPic' @resettingPic='resettingPic'/>
                <!-- <input type="text"  v-model='params.userPic' @keypress.space="checkSpace"><br>    -->
            </div>
            <div class="signuprightdiv">
                <label for="#" >이름</label><br>
                <input type="text"  v-model='params.userName' @keypress.space="checkSpace"><br>                
                <label for="#">이메일</label><br>
                <input class='emailInput' type="text"  v-model='params.userEmail' @keypress.space="checkSpace" @keypress.enter='emailComfirm'><button  v-if='!emailConfirm' class="emailSend" @click='emailComfirm'>메일보내기</button><br>            
                <label for="#">이메일인증</label><br>
                <input id='emailConfirmInput' type="text"  v-model='userEmailConfirm' @keypress.space="checkSpace" @keypress.enter='emailConfirmSend'><button v-if='!emailConfirm' class="emailConfirmSend" @click='emailMatching'>인증요청</button><br>            
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

        <Location :searchLocation='searchLocation' @selectAddress = 'selectAddress' @checkAddress='checkAddress' />
  </div>
</template>

<script>
import Location from '@/components/Sign/Location'
import ProfilePreview from '@/components/Sign/ProfilePreview'


import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'

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
            const btn = document.getElementById('idInput')
            axios.post(`${SERVER_URL}/user/sign/id/${this.params.userId}`)
                .then(res=>{
                    if(res.data.response==='error'){
                        alert(res.data.message)
                    } else {
                        this.idConfirm = true
                        alert(res.data.message)
                        btn.style.width='80%'
                    }
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        nickemailComfirmMet:function(){
            const btn = document.getElementById('nickInput')
            axios.post(`${SERVER_URL}/user/sign/nick/${this.params.userNick}`)
                .then(res => {
                    if(res.data.response==='error'){
                        alert(res.data.message)
                    } else {
                        btn.style.width='80%'
                        alert(res.data.message)
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
                        document.getElementById('emailConfirmInput').style.width='80%'
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


#signup {
    position: absolute;
    height: 100%;
    top:0%;
    width:100%;      
    transition: 0.3s;  
    font-family: font1;
    visibility: hidden;
    animation-fill-mode: forwards;
}
.signuphead {
    position: absolute;
    left: 50%;
    top: 5%;
    font-size: 40px;
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
    color:white;
    font-size: 16px;
}
.signuprightdiv > .emailInput{
    width: 70%;
    
}


.signupform label {
    font-size: 18px;
    margin-top:5%;
}

.signupComplete{
    margin-top: 10%;
    background-color: cornflowerblue;
    color:white;
    border:none;
    border-radius: 10px;
    cursor:pointer;
    font-size: 18px;
}

.backBtn {
    position:absolute;
    margin-top: 2%;
    background-color: transparent;
    border: none;
    color:white;
    border-radius: 10px;
    cursor:pointer;
    font-size: 20px;
    transition:0.3s; 
}
.backBtn:hover{
    color:greenyellow
}
.backBtn > i > span {
    font-family: font1;
    margin-left: 10px;
}

.emailSend, .idConfirm, .nickConfirm, .emailConfirmSend {    
    font-size: 12px;
    border:none;
    border-radius: 10px;
    height:5%; 
    background-color: rgb(114, 114, 114);   
    color:white;
}   

#idInput {
    width: 70%
}

#nickInput {
    width: 70%;
}

#emailConfirmInput {
    width: 72%;
}

#signupPic {
    display: none;
}

.signupPicForm{
    position: relative;
    cursor:pointer;
    margin:0;
    margin-top: 0;
    background-color: rgb(17, 235, 35);
    padding:5px;
    border-radius: 10px;
    font-size: 15px;
    transition:0.3s;
    top: -5%;
}
.signupPicForm:hover{
    background-color: rgb(1, 101, 250);
}
@media screen and (max-width: 501px) {
    
    #signup {
        position: absolute;
        height: 100%;
        top:0%;
        width:100%;      
        transition: 0.3s;  
        font-family: font1;
        visibility: hidden;
        animation-fill-mode: forwards;
    }
    .signupform {
        position: relative;
        width: 100vw;
        height: 100vh;
        top:0;
        left:0;
        transform:none;
        display:block;
        font-size:15px;
        color:white;
        
    }
    .signuphead {
        position: relative;
        left: 0;
        top: 0;
        font-size: 25px;
        color:white;
        transform: none;
        margin: 10% auto;       
    }

    .signupleftdiv{    
        width: 80%;
        height: 80%;        
        margin: 10% auto;
        text-align: left;
    
    }
    .signuprightdiv{   
        width: 80%;
        height: 80%;
        margin: -35% auto;
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
        width: 70%;
        
    }


    .signupform label {
        margin-top:5%;
    }

    .signupComplete{
        position:relative;
        margin-top: 10%;
        margin-left:0%;
        background-color: rgb(247, 247, 247);
        color:black;
        width:100%;
        height:7%;
        border:none;
        border-radius: 10px;
        cursor:pointer;
        font-size: 15px;
    }

    .backBtn {
        position:absolute;
        margin-top: 2%;
        top:0;
        left:10%;
        background-color: transparent;
        border: none;
        font-size: 14px;
        color:white;
        border-radius: 10px;
        cursor:pointer; 
    }

    .backBtn:hover {
        color:white;
    }

    .backBtn > i > span {
        font-family: font1;
        margin-left: 10px;
    }

    .emailSend, .idConfirm, .nickConfirm, .emailConfirmSend {    
        font-size: 15px;
        border:none;
        border-radius: 10px;
        height:5%; 
        background-color: rgba(255, 255, 255, 0.678);   
        color:black;
    }   

    #idInput {
        width: 70%
    }

    #nickInput {
        width: 70%;
    }

    #emailConfirmInput {
        width: 72%;
    }

    #signupPic {
        display: none;
    }

    .signupPicForm{
        position: relative;
        cursor:pointer;
        margin:0;
        margin-top: 0;
        background-color: rgb(255, 255, 255);
        color:black;
        padding:5px;
        border-radius: 10px;
        font-size: 15px;
        transition:0.3s;
        top: 0%;
        width:100%;
        text-align: center;
    }
    .signupPicForm:hover{
        background-color: rgb(1, 101, 250);
    }
}

</style>
