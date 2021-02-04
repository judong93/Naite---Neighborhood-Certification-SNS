<template>
    <nav id="navbar">
        <div class="navBorder"></div>
        <div class="nav-title">
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
            <!-- <input type="text" readonly/>
            <i class="fas fa-search"></i>
            -->
            <!-- <div class='statusHover'>
                
            </div> -->
            <span>user1 님</span>
            <div class="nav-status">
                <div class="statusHover">   
                    <a href="#">내 프로필</a> <br>
                    <a href="#">로그아웃</a>
                </div>
            </div>
        </div>
        


    </nav>
    
</template>
<script>
export default {
    name:'Navbar',
    components:{
                
    },
    data: function() {
        return {
        }
    },
    methods:{
        showCategory:function(num){
            if (num===0) {
                this.$router.push({name:'MainBoard'})
            } else {
                const nowNo = this.$route.params.bigCategoryNo
                console.log(nowNo,num)
                if (nowNo !== num){
                    this.$router.push({name:'Board',params:{bigCategoryNo:num}})
                }
            }
        }
    },
    computed: {

    },

    
}
</script>

<style>
.navBorder {
    position:absolute;
    left:2%;
    top:110%;
    width: 96%;
    border-bottom: 1px solid rgb(179, 179, 179);
    z-index: 0;

}
#navbar {
    position:absolute;
    background-color: rgb(250, 246, 240);
    width:1920px;
    height:77.52px;    
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

.nav-status{
    position: absolute;
    border: solid 1px rgb(214, 213, 213);
    overflow: hidden;
    background-image: url('../../assets/cha2.png');
    background-size: 60px;
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



</style>