import { Box, Button, duration, FormControl, InputLabel, MenuItem, Select, TextField } from "@mui/material";
import React, { useState } from "react";
import { addActivity } from "../Services/API";

const ActivityForm = ({onActivityAdded}) => {
    const [activity, setActivity] = useState({
        type: "RUNNING", duration: '', caloriesBurned: '',
        additionalMetrics: {}
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await addActivity(activity)
            onActivityAdded()
            setActivity({
                type: "RUNNING", duration: '', caloriesBurned: '',
                additionalMetrics: {}
            })
        } catch (error) {
            console.error(error)
        }
    }
  
    return (
        <Box component={"form"} sx={{ mb: 2 }} onSubmit={handleSubmit}>
            <FormControl fullWidth sx={{mb : 2}}>
                <InputLabel>Activity Type</InputLabel>
                <Select
                    value={activity.type}
                    onChange={(e) => {setActivity({...activity, type: e.target.value})}}
                >
                    <MenuItem value="RUNNING">Running</MenuItem>
                    <MenuItem value="WALKING">Walking</MenuItem>
                    <MenuItem value="CYCLING">Cycling</MenuItem>
                    <MenuItem value="SWIMMING">Swimming</MenuItem>
                    <MenuItem value="WEIGHT_TRAINING">Weight Training</MenuItem>
                    <MenuItem value="YOGA">Yoga</MenuItem>
                    <MenuItem value="CARDIO">Cardio</MenuItem>
                    <MenuItem value="STRETCHING">Stretching</MenuItem>
                    <MenuItem value="HIIT">HIIT</MenuItem>
                    <MenuItem value="OTHERS">Others</MenuItem>
                </Select>
            </FormControl>
            <TextField fullWidth
                label="Duration"
                type="number"
                sx={{mb: 2}}
                value={activity.duration}
                onChange={(e) => {setActivity({...activity, duration: e.target.value})}}
            >
            </TextField>
            <TextField fullWidth
                label="Calories Burned"
                type="number"
                sx={{mb: 2}}
                value={activity.caloriesBurned}
                onChange={(e) => {setActivity({...activity, caloriesBurned: e.target.value})}}
            >
            </TextField>
            <Button type="submit" variant="contained">
                Add Activity
            </Button>
        </Box>
    )
}

export default ActivityForm;