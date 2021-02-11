<template>
    <div id='findsign'>
        
        <!-- get /user/sign/{userEmail}  아이디 찾기 -->
        <div class="findId" v-if='findId'>
            <div>
                <h1><span class='backLogo' @click='toLogin'>&#xf30a;</span> 아이디찾기</h1>
                
            </div>
            <label for="">이메일</label><br>
            <input type="text" placeholder="회원가입시 입력했던 이메일을 입력해주세요" id='emailInput' @keypress.enter='findIdMethod' v-model='email'><br>
            <div v-if='userId && findIdBool' style='margin-top:-40px'>회원님의 아이디는 {{userId}} 입니다.</div>
            <div v-if='userId && !findIdBool' style='margin-top:-40px;color:red;'>{{userId}}</div>
            <button @click='findIdMethod'>아이디찾기</button><br>
            <span @click='switchPwId'>비밀번호 찾기</span>
        </div>

        <!-- put /user/sign/password/{userId} 비밀번호 재설정 -->
        <div class="findPw" v-else>
            <h1><span class='backLogo' @click='toLogin'>&#xf30a;</span>비밀번호찾기</h1>

            <div v-if ='!sendingEmail && !changePwBool'>
                <!-- 1이메일과 아이디를 통한 이메일인증코드전송  -->
                <label for="">아이디</label><br>
                <input type="text" placeholder="아이디입력" v-model='pwId'><br>
                <label for="">이메일</label><br>
                <input 
                    id='findPwEmail' v-model='pwEmail' type="text" placeholder="회원가입시 입력했던 이메일을 입력해주세요" 
                    @keypress.enter='sendingEmailMethod'
                ><br>
                <div v-if='pwEmailConfirm' style='margin-top:-40px;color:red;'>유저정보를 확인해주세요</div>
                <button @click='sendingEmailMethod'>
                    인증메일보내기
                </button>
            </div>
            <div v-else-if='sendingEmail  && !changePwBool'>
                <!-- 2이메일인증후 코드 일치여부확인 -->
                <label for="">인증번호</label><br>
                <input id='findPwEmail' type="text" placeholder="이메일의 인증번호를 입력해주세요" @keypress.enter='confirmEmail' v-model='emailCode'><br>    
                <div v-if='codeConfirm' style='margin-top:-40px;color:red;'>코드가 일치하지 않습니다</div>            
                <button @click='confirmEmail'>이메일 인증</button>
            </div>

            <div v-else>
                <!-- 비밀번호일치여부 확인 및 일치할 시 비밀번호 변경 후 로그인페이지로 -->
                <label for="">비밀번호</label><br>
                <input type="password" placeholder="" v-model='changePw'><br>
                <label for="">비밀번호확인</label><br>
                <input id='findPwEmail' v-model='changePwConfirm' type="password" placeholder="" @keypress.enter='changePwMethod'><br>
                <div v-if='!pwSame' style='margin-top:-40px;color:red;'>비밀번호가 일치하지 않습니다.</div>
                <button @click='changePwMethod'>비밀번호변경</button>
            </div>
            <span @click='switchPwId'>아이디찾기</span>
        </div>
    </div>    
</template>
<script>
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'


export default {
    name:'FindSign',
    data:function(){
        return{
            findId:true,
            email:'',
            pwEmail:'',
            pwId:'',
            userId:'',
            findIdBool:false,            
            sendingEmail:false,
            pwEmailConfirm:false,
            changePwBool:false,
            codeConfirm:false,
            emailCode:'',
            changePw:'',
            changePwConfirm:'',
            pwSame:true,
            

        }
    },
    props:{
        toFind:Boolean,
    },
    methods:{
        resetInfo:function(){
            this.pwEmailConfirm=false
            this.sendingEmail = false
            this.email=''
            this.pwEmail=''
            this.pwId =''
            this.userId=''
            this.findIdBool=false
            this.changePwBool=false
            this.codeConfirm=false
            this.emailCode=''
            this.changePw=''
            this.changePwConfirm='' 
            this.pwSame=true
        },
        toLogin:function(){
            this.$emit('switchFindLogin')
            this.resetInfo()
            this.findId=true
        },
        switchPwId:function(){
            if (this.findId){
                this.findId = false        
            } else {
                this.findId = true
            }
            this.resetInfo() 
        },
        findIdMethod:function(){
            this.userId=''
            if (!this.email){
                this.findIdBool = false
                this.userId = '이메일을 입력해주세요.'
                return;
            }

            axios.get(`${SERVER_URL}/user/sign/${this.email}`)
                .then(res => {
                    this.userId = res.data
                    this.findIdBool = true
                })
                .catch(()=>{
                    this.findIdBool = false
                    this.userId = '일치하는 정보가 없습니다. 이메일을 확인해주세요.'
                })
        },
        sendingEmailMethod:function(){
            this.pwEmailConfirm=false
            const params ={
                'type':1,
                'userEmail': this.pwEmail,
                'userId':this.pwId,
            }
            
            axios.post(`${SERVER_URL}/user/sign/email/send`,params)
                .then(res => {
                    console.log(res)
                    if (res.data.response ==='error'){
                        this.pwEmailConfirm=true
                        this.sendingEmail = false
                    } else {
                        this.pwEmailConfirm=false
                        this.sendingEmail = true
                        
                    }
                    
                })
                .catch(err=>{
                    console.log(err)
                    this.pwEmailConfirm=true
                })

        },
        confirmEmail:function(){
            this.codeConfirm = false
            const params = {
                'email': this.pwEmail,
                'key':this.emailCode,
                'type':1,
            }
            console.log(params)
            axios.get(`${SERVER_URL}/user/sign/email/auth`,{params})
                .then(res => {
                    console.log(res)
                    if(res.data.response === 'error') {
                        this.changePwBool = false
                        this.codeConfirm = true
                    } else {
                        this.changePwBool= true
                        this.codeConfirm = false
                    }
                })
                .catch(err =>{
                    console.log(err)
                })
        },
        changePwMethod:function(){
            this.pwSame=true

            if (this.changePw !== this.changePwConfirm) {
                this.pwSame=false
                return ;
            }
            //  put /user/sign/password/{userId}

            axios.put(`${SERVER_URL}/user/sign/password/${this.pwId}`,{
                'userPw':this.changePw
            })
                .then(()=>{
                    alert('비밀번호 재설정완료')
                    this.toLogin()
                    this.resetInfo()
                    
                })
                .catch(err=>{
                    console.log(err)
                    })
        }
    },
    watch:{
        toFind:function(){
            const findsign = document.getElementById('findsign')
            if (!this.toFind) {
                this.findId =true
                findsign.style.webkitAnimationName = 'fadeout'
                findsign.style.webkitAnimationDuration='0s'
                this.resetInfo()
                this.findId = true
            } else {
                this.findId =true
                findsign.style.webkitAnimationName = 'fadein'
                findsign.style.webkitAnimationDuration='1s'
                this.resetInfo()
                this.findId = true
            }
        },
        
    }
}
</script>

<style>

@media screen and (max-width: 500px) {
    #findsign {
        width: 360px;
        height: 640px;
    }
}

#findsign {
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
    visibility: hidden;
    
}

#findsign > div {
    position: absolute;
    top: 50%;
    left:50%;
    color:white;
    transform:translate(-50%,-50%);
    width: 600px;
    height: 800px;    
    text-align: left;
    padding: 100px;
}

#findsign input {
    width: 400px;
    height: 35px;
    background-color: transparent;
    border:none;
    outline:none;
    border-bottom: 1px rgb(255, 255, 255) dotted;
    color:white;

}

#findsign label {
    margin-top: 20px;
    cursor:pointer;
}


.findId h1 {
    text-align: center;
    margin-bottom: 100px;
}
.findPw h1 {
    text-align: center;
    margin-bottom: 50px;
}

.backLogo {
    font-family: 'Font Awesome 5 Free';
    font-weight: 900;
    position:absolute;
    left:100px;
    cursor:pointer;
    transition:0.3s;
    
}

.backLogo:hover {
    left:90px;
}

#emailInput, #findPwEmail {
    margin-bottom: 50px;
}

#findsign button {
    position:relative;
    width: 160px;
    height: 30px;
    margin: 40px 120px;
    color:white;
    background-color: rgb(223, 149, 53);
    border:none;
    outline:none;
    border-radius: 10px;
    cursor:pointer;
    transition:0.3s;
    overflow: hidden;
}

#findsign button:hover {
    background-color: #5dd467;
    color:black;
}

#findsign > div > span {
    text-align: center;
    width: 200px;
    margin: 0 160px;    
    cursor:pointer;
    white-space: nowrap;
    transition:0.3s;
}

#findsign > div > span:hover {
    color:#3F9F47;
}



</style>