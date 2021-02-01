<template>
    <div id="boardlist">
        <div class="listTitle" v-for='(list,idx) in test' :key='idx'> 
            <div class="leftSide" v-if='idx%2===0'>
                <div class='listUser'>
                    <img src="../assets/cha2.png" alt="">
                    <span>{{list.user}}</span>
                </div>
                <div class='listBasicInfo' @click='onDetailMethod'>
                    <div class='title'>
                        {{list.title}}
                    </div>
                    <div class="listInfo">
                        <div>
                            <i class="far fa-comment-dots"></i>
                            {{list.comment}}
                        </div>
                        <div>
                            <i class="far fa-thumbs-up"></i>
                            {{list.like}}
                        </div>
                        <div>
                            <i class="far fa-clock"></i>
                            {{list.created}}
                        </div>
                        
                    </div>
                </div>
            </div>
            <div class="rightSide" v-else>
                
                <div class='listBasicInfo' @click='onDetailMethod'>
                    <div class='title'>
                        {{list.title}}
                    </div>
                    <div class="listInfo">
                        <div>
                            <i class="far fa-comment-dots"></i>
                            {{list.comment}}
                        </div>
                        <div>
                            <i class="far fa-thumbs-up"></i>
                            {{list.like}}
                        </div>
                        <div>
                            <i class="far fa-clock"></i>
                            {{list.created}}
                        </div>
                        
                    </div>
                </div>
                <div class='listUser'>
                    <img src="../assets/cha2.png" alt="">
                    <span>{{list.user}}</span>
                </div>
            </div>
        </div>



    </div>
    
</template>
<script>



export default {
    name:'BoardList',
    components:{
                
    },
    data: function() {
        return {
            test:[
                {'title':'안녕하세요 이번에 여기로 이사온 이동희에요 호호호','user':'이동희','like':3,'comment':5,'created':'15:51'},
                {'title':'여기 맛집아시는분?','user':'김민경','like':3,'comment':5,'created':'15:51'},
                {'title':'동네 괜찮나요?','user':'박주동','like':1,'comment':2,'created':'15:56'},               
                
            ]
        }
    },
    props:{
        onDetail:Boolean,
    },
    methods:{
        onDetailMethod : function(){
            this.$emit('onDetailMethod')
        }

        
    },
    computed: {

    },
    watch:{
        onDetail:function(){
            const listbox=document.getElementById('boardlist')
            const titles = document.querySelectorAll('.title')
            const imgs = document.querySelectorAll('.listUser>img')
            const userName = document.querySelectorAll('.listUser>span')
            const listInfos = document.querySelectorAll('.listInfo')
            const bigLists = document.querySelectorAll('.listTitle')
            if (this.onDetail){
                listbox.style.width ='20%'
                listbox.style.maxHeight ='75%'
                listbox.style.left = '1%'
                listbox.style.top = '23%'
                
                titles.forEach(function(title) {
                    title.style.fontSize = '12px'
                    title.style.height = '50px'
                })
                
                imgs.forEach(function(img) {
                    img.style.width = '40px';
                    img.style.height = '40px';
                })

                userName.forEach(function(name) {
                    name.style.fontSize = '5px';
                })

                listInfos.forEach(function(listInfo) {
                    listInfo.style.display = 'none'
                })
                bigLists.forEach(function(bigList) {
                    bigList.style.display='none'
                })

            } else {
                listbox.style.width ='60%'
                listbox.style.left = '20%'
                listbox.style.top = '33%'
                listbox.style.maxHeight ='65%'
                titles.forEach(function(title) {
                    title.style.fontSize = '20px'
                })
                imgs.forEach(function(img) {
                    img.style.width = '80px';
                    img.style.height = '80px';
                })
                
                userName.forEach(function(name) {
                    name.style.fontSize = '16px';
                })
                listInfos.forEach(function(listInfo) {
                    listInfo.style.display = 'flex'
                })
                bigLists.forEach(function(bigList) {
                    bigList.style.display='block'
                })
      
            }

        }
    },

    
}
</script>

<style>
#boardlist {
    position:absolute;
    width: 60%;
    max-height: 65%;
    background-color:rgb(255, 255, 255);    
    left: 20%;
    border-radius: 20px;
    top: 33%; 
    transition: 0.3s;
    overflow:auto;
    border: 1px solid rgb(224, 224, 224);
    display: block;
}


.leftSide {
    position: relative;
    display:flex;
    margin: 2% 2% 5%;
    height: 80px;
    border-radius: 20px;
    padding: 1%;
}


.rightSide {
    position: relative;
    display:flex;
    margin: 2% 2% 5%;
    height: 80px;
    border-radius: 20px;
    padding: 1%;
    justify-content: flex-end;
}

.listUser {
    position:relative;
    width: 100px;
    height:100px;
    overflow: hidden;
    margin: 0 10px;
    cursor:pointer;
    transition:0.1s;
    
    
}

.listUser > img {
    width: 80px;
    height: 80px;
    border: 1px solid rgb(184, 184, 184);
    border-radius: 100px;
}

.listUser > span {
    font-family: font1;
    display: block;
}



.listBasicInfo {
    font-family: font1;
    position:relative;
    margin-top: 1%;
    width: 100%;
    height: 100%;
    cursor:pointer;
}

.title {
    position:relative;
    border-radius: 20px;
    padding: 10px;
    font-size: 20px;
    background-color:rgba(168,122,79,0.5);
    height: 100%;
    max-width:80%;
}


.leftSide > .listBasicInfo > .title {
    text-align: left;
}


.rightSide > .listBasicInfo > .title {
    text-align: right;
    right:-20%;
}

.listInfo {
    position: relative;
    height:45%;
    width: 80%;
    margin-top: 1%;
    display:flex;
}

.listInfo > div {
    margin: 0 10px;
}

.leftSide > .listBasicInfo > .listInfo {
    justify-content: flex-start;
}

.rightSide > .listBasicInfo > .listInfo {
    left:20%;
    justify-content: flex-end;
}







</style>