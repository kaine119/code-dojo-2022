# Notes from past two days 

- Camera app doesn’t work within the app, takes long and laggy to access pictures within the gallery and upload on to the app
- Takes very long to locate store inside shopping malls and units in condos (IDEA: crowdsourced platform for tips by drivers for drivers)
- Route prescribed is stupid and doesn’t work well, routing experience can be improved:
- (IDEA: cluster deliveries of the same area and driver can choose to order the route personally)
- Issue of obtaining verification code from customers when leaving at the reception: unsure of which package they received


## Call with Ninjavan CPO

Product: 3 main verticals: Commercial, Operations (internal products: sorting system, driver app), New Business Units (new initiatives and strategy team looking at services outside of core offerings by Ninjavan)

Operations: First-mile, middle-mile, last-mile


Questions:
Prescribed routes for drivers:
Bug/ parcel wrongly allocated to driver

Routing service is a separate service system calls that tries to automate and optimise the generat
ion of the route. Travelling Salesman Problem: takes long to compute and difficult. Traffic information, maps of routes, timeslots of parcels used as parameters to produce the routes of deliveries for each driver.

Failed parcel rate: 5-10%
Multiple reasons for failures: different categories to process:
 1. Rescheduling, drivers paid on per-parcel delivery, paid upon success, so marginal costs only.
 2. Recovery process when customers not contactable. SOPs there: return to sender? Check with shipper on what to do? Higher costs here.


What happens upstream:

Typical lifecycle of a parcel:
- First mile: shipper creates an order and pickup job created for drivers. Specialised drivers for major shippers. These parcels are picked up and sorted overnight for delivery the next day. 
- Middle mile: Sends parcels from main sort hubs to delivery stations in Singapore. In other countries: can take up to a few days from main sort hubs to last-mile stations and different options of shipping.
- Last mile: delivery to households and final destination.

Flow of information: 
- Creating more transparency in the system: how to improve accountability and signatures by middlemen and consignees and customers.

Priority Deliveries:
- Must be attempted delivery by end of today, if missed: penalties involved.

Data processed in realtime generally, only routing data processed in batches. 
Data all stored in the cloud.

3 things NV cares about most:
1. Productivity of drivers and ops: speed of deliveries, sorters, how to incentivise them to work faster and better
2. Service quality provided to both shippers and customers: improving the experience for both, especially on the digital front.
3. Engagement with the internal employees: ENPS Employee Net Promoter Score, their levels of happiness and motivation.

Customer experience and communications:
- Trying to improve shipper experience, different communication channels: NinjaChat via instant-messaging. A lot of it is very reactive: responding to an issue and solving the problem. Trying to be a lot more proactive: reach out to customers once we know that there is a problem, even before they reach out to NV.
- Backend stuff they are working on: any parcel that flows out of happy flow: automating the process and feeding the info back into the system to deal with it.
- FSR Fantastic Service Recovery: shippers would be able to reschedule pickup and delivery directly via live-chat or forms and dynamically reschedule the deliveries.

Reserve fleet:
- When drivers do not turn up suddenly: putting parcels into batches and notifying pool of drivers to pick them up. Problem was with network effect and scaling issue.

Social commerce idea:
- For logistics: Main contractors engaged and then there will be social-commerce deliverers allocated for delivery.
- For sales: lead qualification process: the greater the number of touchpoints, the more likely the customer will drop out of the onboarding process.
- Details required: varying requirements across countries and type of shipper.
- Mostly a self signup: create email account and fill in the details.
- EQUIC: verify their identity and confirm that they are not shipping illegal stuff
- Bank account information 

Apart from ninja chat on telegram and text message, is there any other way of updating customer about their packages?
- Emails
- Tracking page using tracking ID
- Automated way of checking whether shipper is repeat shipper.

Should internet connection or the infrastructure be down, are there manual methods of processing or backup systems in place?
- Information stored locally first and then updated to the cloud when service resumes.
- Caching, persistency, synchronisation. 
- Offline-first experiences for all clients
- Must be able to shift data between availability zones within a service provider and also to other service providers.


Motivating drivers through incentives:
- Community formed within drivers and sorters: how do we tap on that using tech?
