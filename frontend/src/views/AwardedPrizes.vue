<template>
<div class="container">
  <h1>Our Family's Prizes</h1>
  <div id="top-image">
    <img id="bookshelfImage" src='@/assets/images/fireworks.jpg'/>
  </div>
  <div id="filtersAndSummary">
    <div class="form-group">
      <h4>Filter</h4>
      <button class="btn btn-info filterButton" v-on:click="changeFilter(0)">All</button>
      <button class="btn btn-info filterButton" v-for="familyMember in familyMembers" v-bind:key="familyMember.id" 
      v-bind:value="familyMember.id" v-on:click="changeFilter(familyMember.id)"
      >
          {{familyMember.username}}
      </button>
    </div>
  </div>
  <h4>Recent awards</h4>
  <div id="log-display-container">
    <div class="log" v-for="award in awardsToShow" v-bind:key="award.awardId">
      <p><span class="grey">Reader :</span> {{award.userName}} </p>
      <p><span class="grey">Prize Name :</span> {{award.prizeName}} </p>
      <p><span class="grey">Description :</span> {{award.prizeDescription}}</p>
      <p><span class="grey">Date Achieved :</span> {{award.awardedDate}}</p>
    </div>
    <div class="log" id="blank-log" v-if="awards.length % 2 == 1">
    </div>
  </div>
</div>
</template>

<script>
import auth from '@/auth';
export default {
    props: {
      currentUser: Object
    },
    data() {
        return {
            awards: [],
            familyMembers: [],
            currentFilter: 0,
            count: 0
        }
    },

    methods: {
        getAllAwardedPrizes() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/familyawardedprizes/${this.currentUser.familyId}`, {
            method: 'GET',
                headers: {
                Accept: 'application/json',
                Authorization: 'Bearer ' + auth.getToken()
            }
            })
            .then((response) => {
                return response.json();
            
            })
            .then((data) => {
                this.awards = data;
            })
        },
        changeFilter(familyId) {
            this.currentFilter = familyId;
            this.count++;
        },
        getAllFamilyMembers() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/users/${this.currentUser.familyId}`, {
                method: 'GET',
                headers: {
                Accept: 'application/json',
                Authorization: 'Bearer ' + auth.getToken()
                }
                })
                .then((response) => {
                    return response.json();
                })
                .then((data) => {
                    this.familyMembers = data;
                });
        },
        // getAwardedPrizesByUser() {
        //     fetch(`${process.env.VUE_APP_REMOTE_API}/api/userawardedprizes/${this.currentFilter}`, {
        //       method: 'GET',
        //       headers: {
        //         Accept: 'application/json',
        //         Authorization: 'Bearer ' + auth.getToken()
        //       }
        //     })
        //     .then((response) => {
        //       return response.json();
        //     })
        //     .then((data) => {
        //       this.awards = data;
        //     });
        // }
    },
    created() {
        this.getAllAwardedPrizes();
        this.getAllFamilyMembers();
    },
    computed: {
    awardsToShow: function() {
        if (this.currentFilter === 0) {
            return this.awards;
        } else {
            return this.awards.filter(award => award.userId === this.currentFilter)
        }
    }
  }
}
</script>

<style scoped>
p {
 margin: 0;
}
span.grey {
  color: grey;
}
div#filtersAndSummary {
  display: flex;
  margin-bottom: 2%;
}
#log-display-container {
  display: flex;
  flex-wrap: wrap;
}
#form {
  border: 1px solid grey;
  border-radius: 10px;
  padding: 10px;
  padding-bottom: 0;
  width: 60%;
  margin: 0;
}
div.log {
    border: 2px solid lightgrey;
    border-radius: 10px;
    margin-top: 2%;
    padding: 5px;
    width: 45%;
    margin-left: auto;
    margin-right: auto;
}
.filterButton {
  margin-top: 5%;
  margin-right: 5%;
  margin-bottom: 5%;
}
div.container {
  margin-bottom: 3%;
}
div#activitySummary {
  margin-left: 10%;
}
div#addBookOption {
  position: absolute;
  right: 0;
  margin-right: 42%;
}
div#addBookOption p {
  margin-top: 8%;
}
div#blank-log {
  border: none;
}

div#top-image {
    max-height: 200px; 
    /* make responsive */
    overflow: hidden;
    border-radius: 15px;
    margin-bottom: 2%;
  }

  #bookshelfImage {
    width: 91vw;
    height: auto;
       /* make responsive */
  }
</style>