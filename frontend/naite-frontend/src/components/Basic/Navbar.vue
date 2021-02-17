<template>
    <nav id="navbar">
        <div class="webNav">
            <div class="navBorder"></div>
            <div class="nav-title" @click='showCategory(0)'>
                나의 이웃테두리: 나이테
            </div>
            <div class="nav-link">
                <a href="#" @click='showCategory(0)'>골목길</a>
                <a href="#" @click='showCategory(1)'>번화가</a>
                <a href="#" @click='showCategory(2)'>동사무소</a>
                <a href="#" @click='showCategory(3)'>수군수군</a>
                <a href="#" @click='showCategory(4)'>소리소문</a>
                <a href="#" @click='showCategory(5)'>장터</a>
                <a href="#" @click='showCategory(6)'>확성기</a>
            </div>
            <div class="nav-right">
                <i class="fas fa-search" @click="startSearch" ></i>
                <input type="text" @click='realSearch'>
                <span @click="startSearch">Click here >></span>
                <i class="fas fa-times-circle" @click='closeSearch'></i>
                <span>{{greeting}}</span>
                <div class="nav-status">
                    <div class="statusHover">   
                        
                        <a href="#" @click='profile'>내 프로필</a> <br>
                        <a href="#" @click='logout'>로그아웃</a>
                    </div>
                </div>
            </div>
        </div>

        <div class='mobileNav'>
            <div class='mobileNavHeader'>
                <img src="../../assets/naitelogo.png" alt="" width='30%'>
            </div>
            <div class='mobileNavBottom'>                
                <div @click='mobileMenu'>
                    <i class="fas fa-bars"></i><br>
                    <span>Menu</span>
                </div>
                <div @click='mobileMessage'>
                    <i class="far fa-comments"></i><br>
                    <span>Message</span>
                </div>
                <div @click='showCategory(0)'>
                    <i class="fas fa-home"></i><br>
                    <span>Home</span>
                </div>
                <div @click='mobileWrite()'>
                    <i class="fas fa-pen-alt"></i><br>
                    <span>Write</span>
                </div>
                <div @click='profile'>
                    <i class="fas fa-user-alt"></i><br>
                    <span>My profile</span>
                </div>
            </div>

        </div>

        <div>
        <div class="menu">
            <div @click='showCategory(1)'>
                <div class='menu1'>
                    <img src="../../assets/번화가.png" alt="" width='100%'>
                </div>
                <span>번화가</span>
                
            </div>
            <div @click='showCategory(2)'>
                <div class='menu2'>
                    <img src="../../assets/동사무소.png" alt="" width='100%'>
                </div>
                <span>동사무소</span>
                
            </div>
            <div @click='showCategory(3)'>
                <div class='menu3'>
                    <img src="../../assets/수군수군2.png" alt="" width='100%'>
                </div>
                <span>수군수군</span>
                
            </div>
            <div @click='showCategory(4)'>
                <div class='menu4'>
                    <img src="../../assets/소리소문1.png" alt="" width='100%'>
                </div>
                <span>소리소문</span>
                
            </div>
            <div @click='showCategory(5)'>
                <div class='menu5'>
                    <img src="../../assets/장터1.png" alt="" width='100%'>
                </div>
                <span>장터</span>
            </div>
            <div @click='showCategory(6)'>
                <div class='menu6'>
                    <i class="fas fa-bullhorn"></i>
                </div>
                <span>확성기</span>
            </div>
            <div @click='searchTitle'>
                <div class='menu7'>

                    <i class="fab fa-sistrix"></i>
                </div>
                <span>검색</span>
            </div>
        </div>


        </div>


    </nav>
    
</template>
<script>
import jwt_decode from 'jwt-decode'
import _ from 'lodash'


export default {
    name:'Navbar',
    components:{
                
    },
    data: function() {
        return {
            greeting:'',
            userNo:'',
            mobileMenuBoo:false,
        }
    },
    methods:{
        searchTitle:function(){
            this.$router.push({name:'Search'})
            this.mobileMenu()            
            
        },
        realSearch:function(){
            const searchInput = document.querySelector('.fa-search+input')
            searchInput.style.width = '700px'
            searchInput.style.top = '388px'
            searchInput.style.right = '610px'
            searchInput.style.height='50px'
            setTimeout(() => {
                searchInput.style.width='0px'
                searchInput.style.backgroundColor = 'transparent'
                setTimeout(() => {
                    this.closeSearch()
                }, 500);
            }, 500);
            this.$router.push({name:'Search'})
        },
        startSearch:function(){
            const icon = document.querySelector('.fa-search')
            const searchInput = document.querySelector('.fa-search+input')
            const searchSpan = document.querySelector('.fa-search+input+span')
            const xbtn = document.querySelector('.fa-times-circle')
            icon.style.right = '140%'
            searchInput.style.width = '150px'
            searchInput.style.backgroundColor = '#A87A4F'
            searchSpan.style.display='none'
            xbtn.style.display='initial'
        },
        closeSearch:function(){
            const icon = document.querySelector('.fa-search')
            const searchInput = document.querySelector('.fa-search+input')
            const searchSpan = document.querySelector('.fa-search+input+span')
            const xbtn = document.querySelector('.fa-times-circle')
            icon.style.right = '100%'
            searchInput.style.width = '0px'
            searchInput.style.right = '100%'
            searchInput.style.top = '50%'
            searchInput.style.backgroundColor = 'transparent'
            searchSpan.style.display='initial'
            xbtn.style.display='none'
            searchInput.style.height='26px'
        },
        showCategory:function(num){
            this.mobileMenuBoo = true
            this.mobileMenu()            
            if (num===0) {
                this.$router.push({name:'MainBoard'})
            } else if (num===5){
                this.$router.push({name:'MarketBoard'})
            } else if (num===3){
                this.$router.push({name:'ReviewBoard'})
            } else {
                const nowNo = this.$route.params.bigCategoryNo
                if (nowNo !== num){
                    this.$router.push({name:'Board',params:{bigCategoryNo:num}})
                }
            }
        },
        logout:function(){
            localStorage.removeItem('jwt')
            this.$router.push({name:'Sign'})
            this.$store.dispatch('logout')
        },
        profile:function(){
            this.$router.push({name:'Profile', params:{userNo:this.userNo}})
        },
        mobileMenu:function(){
            let category = document.querySelectorAll('.menu > div')
            let i = 1
            let color =['rgba(53,105,165,0.7)','rgba(0,200,252,0.7)','rgba(252,186,39,0.7)','rgba(221,75,57,0,7)','rgba(247,230,0,0.7)','rgba(45,180,0,0.7)','rgba(168,122,79,0.7)']
            if (!this.mobileMenuBoo){
                category.forEach(ca => {
                    const top =700+i*(-75)
                    const caBtn = document.querySelector(`.menu${i}`)
                    let j = _.random(0,color.length-1)
                    ca.style.visibility='visible'
                    ca.style.top = `${top}px`
                    caBtn.style.backgroundColor = color[j]
                    color.splice(j,1)
                    i += 1
                    this.mobileMenuBoo = true
                    
                });
            } else {
                category.forEach(ca=>{
                    ca.style.visibility = 'hidden'
                    ca.style.top = '768px'
                })
                this.mobileMenuBoo=false
            }
        },
        mobileWrite:function(){
            this.$router.push({name:'Posting'})
        },
        mobileMessage:function(){
            this.$router.push({name:'MobileMessage',params:{'roomNo':-1,'otherNick':'동룡'}})
        }
    },
    computed: {

    },
    created(){
        const token = localStorage.getItem('jwt')
        const decode = jwt_decode(token)
        this.userNo = decode.user.userNo
        this.greeting = decode.greeting          
        setTimeout(() => {
            const navImg = document.querySelector('.nav-status')   
            navImg.style.backgroundImage= `url(${decode.userPic})`
        }, 100);
    }

    
}
</script>

<style>
.webNav {
    width:100%;
    display: flex;
}
.navBorder {
    position:absolute;
    left:2%;
    top:110%;
    width: 96%;
    height:0;
    border-bottom: 1px solid rgb(179, 179, 179);
    z-index: 0;
}
#navbar {
    position:absolute;
    background-color: rgb(250, 246, 240);
    width:1920px;
    /* height:77.52px;     */
    height:80px;
    display: flex;
    font-family: font1; 
    z-index: 1;
    
}

.nav-title {
    position: relative;
    color:#3F9F47;
    width: 20%;
    padding: 20px;
    padding-left: 35px;
    font-size: 32px;
    cursor:pointer;
}



.nav-link {
    width: 60%;
    font-size: 20px;
    margin-top: 20px;
    text-decoration: none;
    
}

.nav-link > a {
    margin-right: 5%;
    width: 100%;
    color:black;
    text-align: center;
    text-decoration: none;
    transition: 0.2s;
}

.nav-link > a:hover {
    color:#A87A4F;
    text-decoration: none;
    font-size: 110%;
}



.nav-right{
    position:relative;
    text-align: center;
    width:20%;
    display:flex;
    justify-content: space-evenly;
}

.nav-right > span {
    margin-top: 30px;
    margin-right: 130px;
}

.nav-status{
    position: absolute;
    border: solid 1px rgb(214, 213, 213);
    overflow: hidden;
    background-image: url('../../assets/cha2.png');
    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-position: center;
    width:60px;
    height:60px;
    border-radius: 100%;
    cursor:pointer;
    transition: 0.4s;
    transform: translate(-50%,-50%);
    top: 50%;
    right: 25%;
    background-color:rgb(250, 246, 240) ;
    
    
}

.nav-status:hover {
    border-radius:0;
    width: 150px;
    height: 150px;
    right: 5%;
    transform: translate(0,0);
    background-position-x: center;
    background-position-y: 20px;
    border: none;
}
.nav-status:hover > .statusHover {
    display: flex;
    justify-content:space-between;
    align-content: flex-end;
    height: 150px; 
}
.nav-status:hover > .statusHover > a {
    margin: 100px 10px;
    font-size: 15px;
}

.statusHover {
    display: none;
    transition: 0.3s;
}

.mobileNav{
    display:none;
}
.menu {
    display:none
}

.fa-search {
    position:absolute;
    top: 50%;
    transform:translateY(-50%);
    right:100%;
    
    font-size: 20px;
    transition:0.3s;
    cursor:pointer;
}

.fa-search+input{
    position: absolute;
    /* display:none; */
    width: 0px;
    top: 50%;
    transform:translateY(-50%);
    right:100%;
    border:none;
    outline: none;
    background-color: transparent;
    border-radius: 10px;
    transition:0.3s;
}

.fa-search+input+span{
    white-space: nowrap;
    position: absolute;
    top:15%;
    transform: translateY(-50%);
    right:75%;
    transition:0.3s;
    cursor:pointer;
}

.fa-search+input+span:hover {
    right: 80%;
    color: blue;
}

.fa-search:hover+input+span {
    right: 80%;
    color: blue;
}

.fa-times-circle {
    color:red;
    position:absolute;
    font-size: 20px;
    right:93%;
    top:38%;
    transition: 0.6s;
    cursor:pointer;
    display:none;
    
}
.fa-times-circle:hover{
    transform:rotate(180deg)
}

@media screen and (max-width: 501px){
    #navbar {
        position:relative;
        /* background-color:transparent; */
        width:0;
        height:0;    
        z-index: 1;
        bottom:0;
    
    }
    .navBorder {
        position:absolute;
        left:0%;
        top:0%;
        width: 100vw;
        height:100vh;
    }
    .webNav {
        display:none;
    }
    .mobileNav{        
        display:initial
    }
    .mobileNavHeader{
        position:fixed;
        height: 7%;
        width: 100%;
        background-color: rgb(250, 246, 240);
        box-shadow:0 0 3px black;
        -webkit-backface-visibility: hidden;
    }
    .mobileNavHeader > img {
        margin-top: 3%;

    }


    .mobileNavBottom{
        position:fixed;
        left:0;
        bottom:0;
        width: 100%;
        height:7%;        
        background-color:#3F9F47;
        box-shadow:2px 0 0 black;
        padding-top: 5px;
        display: flex;        
        -webkit-backface-visibility: hidden;
    }
    .mobileNavBottom > div {
        width:20%;
        height:100%;
        color:rgba(255, 255, 255, 0.8);
    }
    .menu{
        display: initial;
        background-color: rgba(0,0,0,1);
        
    }
    .menu > div{

        visibility: hidden;
        position:fixed;
        top:768px;
        left:20px;
        transition: 0.3s;
        background-color: rgba(255,255,255,0.9);
        border-radius: 5px;
        padding:1px 3px;
        
    }
    .menu > div > div {
        overflow: hidden;
        width: 50px;
        height: 50px;
        background-color: teal;  
        padding-top: 10px;
        border-radius: 100%;
        font-size: 20px;
        color:black;
        
    }
    .menu > div > span {
        color:black;
        white-space: nowrap;
        font-size: 12px;
    }

}



</style>