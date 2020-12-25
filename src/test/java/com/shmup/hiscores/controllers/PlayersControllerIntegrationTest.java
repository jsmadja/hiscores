package com.shmup.hiscores.controllers;

import com.shmup.hiscores.ContainerDatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayersControllerIntegrationTest extends ContainerDatabaseTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void get_all_players() throws Exception {
        String jsonContent = """
                [
                  {id:1,name:'anzymus'},
                  {id:2,name:'Mickey'}
                ]
                """;
        this.mvc.perform(get("/players")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }

    @Test
    void get_versus() throws Exception {
        String jsonContent = """
                {
                 	"player1": {
                 		"id": 1,
                 		"createdAt": "2013-11-29T19:13:32.000+00:00",
                 		"name": "anzymus",
                 		"shmupUserId": 33489,
                 		"hideMedals": false,
                 		"vip": true,
                 		"superAdministrator": true,
                 		"authenticated": true,
                 		"administrator": true
                 	},
                 	"player2": {
                 		"id": 2,
                 		"createdAt": "2013-11-29T19:13:32.000+00:00",
                 		"name": "Mickey",
                 		"shmupUserId": 33490,
                 		"hideMedals": false,
                 		"vip": true,
                 		"superAdministrator": false,
                 		"authenticated": true,
                 		"administrator": false
                 	},
                 	"comparisons": [{
                 		"game": {
                 			"id": 1,
                 			"createdAt": "2014-08-15T12:14:58.000+00:00",
                 			"thread": "http://forum.shmup.com/viewtopic.php?t=8620&f=20",
                 			"cover": "/covers/172.jpg",
                 			"title": "Strikers 1945 PLUS",
                 			"platforms": [{
                 				"id": 1,
                 				"createdAt": "2014-08-15T12:14:58.000+00:00",
                 				"name": "NG"
                 			}, {
                 				"id": 2,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "PCB"
                 			}, {
                 				"id": 3,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "X360"
                 			}],
                 			"difficulties": [{
                 				"id": 1,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "Original",
                 				"sortOrder": 1
                 			}, {
                 				"id": 2,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "Hard",
                 				"sortOrder": 2
                 			}, {
                 				"id": 3,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "Very Hard",
                 				"sortOrder": 3
                 			}],
                 			"modes": [{
                 				"id": 1,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "Black Label",
                 				"sortOrder": 20,
                 				"scoreType": ""
                 			}, {
                 				"id": 2,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "White Label",
                 				"sortOrder": 30,
                 				"scoreType": ""
                 			}],
                 			"ships": [{
                 				"id": 1,
                 				"createdAt": "2013-12-01T00:36:53.000+00:00",
                 				"name": "Type I",
                 				"sortOrder": 1
                 			}],
                 			"stages": [{
                 				"id": 1,
                 				"createdAt": "2014-08-15T12:14:58.000+00:00",
                 				"name": "1-4",
                 				"sortOrder": 3
                 			}]
                 		},
                 		"difficulty": {
                 			"id": 1,
                 			"createdAt": "2013-11-29T19:12:15.000+00:00",
                 			"name": "Original",
                 			"sortOrder": 1
                 		},
                 		"mode": {
                 			"id": 1,
                 			"createdAt": "2013-11-29T19:12:15.000+00:00",
                 			"name": "Black Label",
                 			"sortOrder": 20,
                 			"scoreType": ""
                 		},
                 		"score1": {
                 			"id": 3,
                 			"createdAt": "2013-11-30T16:59:00.000+00:00",
                 			"game": {
                 				"id": 1,
                 				"createdAt": "2014-08-15T12:14:58.000+00:00",
                 				"thread": "http://forum.shmup.com/viewtopic.php?t=8620&f=20",
                 				"cover": "/covers/172.jpg",
                 				"title": "Strikers 1945 PLUS",
                 				"platforms": [{
                 					"id": 1,
                 					"createdAt": "2014-08-15T12:14:58.000+00:00",
                 					"name": "NG"
                 				}, {
                 					"id": 2,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "PCB"
                 				}, {
                 					"id": 3,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "X360"
                 				}],
                 				"difficulties": [{
                 					"id": 1,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "Original",
                 					"sortOrder": 1
                 				}, {
                 					"id": 2,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "Hard",
                 					"sortOrder": 2
                 				}, {
                 					"id": 3,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "Very Hard",
                 					"sortOrder": 3
                 				}],
                 				"modes": [{
                 					"id": 1,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "Black Label",
                 					"sortOrder": 20,
                 					"scoreType": ""
                 				}, {
                 					"id": 2,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "White Label",
                 					"sortOrder": 30,
                 					"scoreType": ""
                 				}],
                 				"ships": [{
                 					"id": 1,
                 					"createdAt": "2013-12-01T00:36:53.000+00:00",
                 					"name": "Type I",
                 					"sortOrder": 1
                 				}],
                 				"stages": [{
                 					"id": 1,
                 					"createdAt": "2014-08-15T12:14:58.000+00:00",
                 					"name": "1-4",
                 					"sortOrder": 3
                 				}]
                 			},
                 			"player": {
                 				"id": 1,
                 				"createdAt": "2013-11-29T19:13:32.000+00:00",
                 				"name": "anzymus",
                 				"shmupUserId": 33489,
                 				"hideMedals": false,
                 				"vip": true,
                 				"superAdministrator": true,
                 				"authenticated": true,
                 				"administrator": true
                 			},
                 			"stage": null,
                 			"mode": {
                 				"id": 1,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "Black Label",
                 				"sortOrder": 20,
                 				"scoreType": ""
                 			},
                 			"difficulty": {
                 				"id": 1,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "Original",
                 				"sortOrder": 1
                 			},
                 			"ship": null,
                 			"platform": {
                 				"id": 1,
                 				"createdAt": "2014-08-15T12:14:58.000+00:00",
                 				"name": "NG"
                 			},
                 			"comment": "All(Lx4) Reco Normal",
                 			"photo": null,
                 			"inp": null,
                 			"replay": "1",
                 			"value": 425027420,
                 			"onecc": true,
                 			"progression": null,
                 			"rank": 2,
                 			"gapWithPreviousScore": null,
                 			"vip": true,
                 			"1CC": false,
                 			"createdSinceInFrench": "il y a 7 ans",
                 			"timeScore": false,
                 			"gameTitle": "Strikers 1945 PLUS Black Label Original",
                 			"stageName": ""
                 		},
                 		"score2": {
                 			"id": 1,
                 			"createdAt": "2013-11-30T16:59:00.000+00:00",
                 			"game": {
                 				"id": 1,
                 				"createdAt": "2014-08-15T12:14:58.000+00:00",
                 				"thread": "http://forum.shmup.com/viewtopic.php?t=8620&f=20",
                 				"cover": "/covers/172.jpg",
                 				"title": "Strikers 1945 PLUS",
                 				"platforms": [{
                 					"id": 1,
                 					"createdAt": "2014-08-15T12:14:58.000+00:00",
                 					"name": "NG"
                 				}, {
                 					"id": 2,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "PCB"
                 				}, {
                 					"id": 3,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "X360"
                 				}],
                 				"difficulties": [{
                 					"id": 1,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "Original",
                 					"sortOrder": 1
                 				}, {
                 					"id": 2,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "Hard",
                 					"sortOrder": 2
                 				}, {
                 					"id": 3,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "Very Hard",
                 					"sortOrder": 3
                 				}],
                 				"modes": [{
                 					"id": 1,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "Black Label",
                 					"sortOrder": 20,
                 					"scoreType": ""
                 				}, {
                 					"id": 2,
                 					"createdAt": "2013-11-29T19:12:15.000+00:00",
                 					"name": "White Label",
                 					"sortOrder": 30,
                 					"scoreType": ""
                 				}],
                 				"ships": [{
                 					"id": 1,
                 					"createdAt": "2013-12-01T00:36:53.000+00:00",
                 					"name": "Type I",
                 					"sortOrder": 1
                 				}],
                 				"stages": [{
                 					"id": 1,
                 					"createdAt": "2014-08-15T12:14:58.000+00:00",
                 					"name": "1-4",
                 					"sortOrder": 3
                 				}]
                 			},
                 			"player": {
                 				"id": 2,
                 				"createdAt": "2013-11-29T19:13:32.000+00:00",
                 				"name": "Mickey",
                 				"shmupUserId": 33490,
                 				"hideMedals": false,
                 				"vip": true,
                 				"superAdministrator": false,
                 				"authenticated": true,
                 				"administrator": false
                 			},
                 			"stage": null,
                 			"mode": {
                 				"id": 1,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "Black Label",
                 				"sortOrder": 20,
                 				"scoreType": ""
                 			},
                 			"difficulty": {
                 				"id": 1,
                 				"createdAt": "2013-11-29T19:12:15.000+00:00",
                 				"name": "Original",
                 				"sortOrder": 1
                 			},
                 			"ship": null,
                 			"platform": {
                 				"id": 1,
                 				"createdAt": "2014-08-15T12:14:58.000+00:00",
                 				"name": "NG"
                 			},
                 			"comment": "All(Lx4) Reco Normal",
                 			"photo": null,
                 			"inp": null,
                 			"replay": "1",
                 			"value": 425027421,
                 			"onecc": true,
                 			"progression": null,
                 			"rank": 1,
                 			"gapWithPreviousScore": null,
                 			"vip": true,
                 			"1CC": false,
                 			"createdSinceInFrench": "il y a 7 ans",
                 			"timeScore": false,
                 			"gameTitle": "Strikers 1945 PLUS Black Label Original",
                 			"stageName": ""
                 		},
                 		"lostByPlayer1": true,
                 		"wonByPlayer1": false,
                 		"scoreGap": 0
                 	}]
                 }
                """;
        this.mvc.perform(get("/players/1/versus/2")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }

    @Test
    void get_player_scores() throws Exception {
        String jsonContent = """
                [{
                 	"id": 3,
                 	"createdAt": "2013-11-30T16:59:00.000+00:00",
                 	"game": {
                 		"id": 1,
                 		"createdAt": "2014-08-15T12:14:58.000+00:00",
                 		"thread": "http://forum.shmup.com/viewtopic.php?t=8620&f=20",
                 		"cover": "/covers/172.jpg",
                 		"title": "Strikers 1945 PLUS",
                 		"platforms": [{
                 			"id": 1,
                 			"createdAt": "2014-08-15T12:14:58.000+00:00",
                 			"name": "NG"
                 		}, {
                 			"id": 2,
                 			"createdAt": "2013-11-29T19:12:15.000+00:00",
                 			"name": "PCB"
                 		}, {
                 			"id": 3,
                 			"createdAt": "2013-11-29T19:12:15.000+00:00",
                 			"name": "X360"
                 		}],
                 		"difficulties": [{
                 			"id": 1,
                 			"createdAt": "2013-11-29T19:12:15.000+00:00",
                 			"name": "Original",
                 			"sortOrder": 1
                 		}, {
                 			"id": 2,
                 			"createdAt": "2013-11-29T19:12:15.000+00:00",
                 			"name": "Hard",
                 			"sortOrder": 2
                 		}, {
                 			"id": 3,
                 			"createdAt": "2013-11-29T19:12:15.000+00:00",
                 			"name": "Very Hard",
                 			"sortOrder": 3
                 		}],
                 		"modes": [{
                 			"id": 1,
                 			"createdAt": "2013-11-29T19:12:15.000+00:00",
                 			"name": "Black Label",
                 			"sortOrder": 20,
                 			"scoreType": ""
                 		}, {
                 			"id": 2,
                 			"createdAt": "2013-11-29T19:12:15.000+00:00",
                 			"name": "White Label",
                 			"sortOrder": 30,
                 			"scoreType": ""
                 		}],
                 		"ships": [{
                 			"id": 1,
                 			"createdAt": "2013-12-01T00:36:53.000+00:00",
                 			"name": "Type I",
                 			"sortOrder": 1
                 		}],
                 		"stages": [{
                 			"id": 1,
                 			"createdAt": "2014-08-15T12:14:58.000+00:00",
                 			"name": "1-4",
                 			"sortOrder": 3
                 		}]
                 	},
                 	"player": {
                 		"id": 1,
                 		"createdAt": "2013-11-29T19:13:32.000+00:00",
                 		"name": "anzymus",
                 		"shmupUserId": 33489,
                 		"hideMedals": false,
                 		"vip": true,
                 		"superAdministrator": true,
                 		"authenticated": true,
                 		"administrator": true
                 	},
                 	"stage": null,
                 	"mode": {
                 		"id": 1,
                 		"createdAt": "2013-11-29T19:12:15.000+00:00",
                 		"name": "Black Label",
                 		"sortOrder": 20,
                 		"scoreType": ""
                 	},
                 	"difficulty": {
                 		"id": 1,
                 		"createdAt": "2013-11-29T19:12:15.000+00:00",
                 		"name": "Original",
                 		"sortOrder": 1
                 	},
                 	"ship": null,
                 	"platform": {
                 		"id": 1,
                 		"createdAt": "2014-08-15T12:14:58.000+00:00",
                 		"name": "NG"
                 	},
                 	"comment": "All(Lx4) Reco Normal",
                 	"photo": null,
                 	"inp": null,
                 	"replay": "1",
                 	"value": 425027420,
                 	"onecc": true,
                 	"progression": null,
                 	"rank": 2,
                 	"gapWithPreviousScore": null,
                 	"vip": true,
                 	"1CC": false,
                 	"createdSinceInFrench": "il y a 7 ans",
                 	"timeScore": false,
                 	"gameTitle": "Strikers 1945 PLUS Black Label Original",
                 	"stageName": ""
                 }]
                """;
        this.mvc.perform(get("/players/1/scores")
                .cookie(createShmupCookie())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }

}
